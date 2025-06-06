package ryd.checknm.dashboard.module.bpm.framework.flowable.core.candidate.strategy.dept;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import ryd.checknm.dashboard.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateStrategy;
import ryd.checknm.dashboard.module.system.api.dept.DeptApi;
import ryd.checknm.dashboard.module.system.api.dept.dto.DeptRespDTO;
import ryd.checknm.dashboard.module.system.api.user.AdminUserApi;
import ryd.checknm.dashboard.module.system.api.user.dto.AdminUserRespDTO;
import jakarta.annotation.Resource;

import java.util.*;

/**
 * 部门的负责人 {@link BpmTaskCandidateStrategy} 抽象类
 *
 * @author jason
 */
public abstract class AbstractBpmTaskCandidateDeptLeaderStrategy implements BpmTaskCandidateStrategy {

    @Resource
    protected DeptApi deptApi;
    @Resource
    protected AdminUserApi adminUserApi;

    /**
     * 获得指定层级的部门负责人，只有第 level 的负责人
     *
     * @param dept 指定部门
     * @param level 第几级
     * @return 部门负责人的编号
     */
    protected Long getAssignLevelDeptLeaderId(DeptRespDTO dept, Integer level) {
        Assert.isTrue(level > 0, "level 必须大于 0");
        if (dept == null) {
            return null;
        }
        DeptRespDTO currentDept = dept;
        for (int i = 1; i < level; i++) {
            DeptRespDTO parentDept = deptApi.getDept(currentDept.getParentId());
            if (parentDept == null) { // 找不到父级部门，到了最高级。返回最高级的部门负责人
                break;
            }
            currentDept = parentDept;
        }
        return currentDept.getLeaderUserId();
    }

    /**
     * 获得连续层级的部门负责人，包含 [1, level] 的负责人
     *
     * @param deptIds 指定部门编号数组
     * @param level 最大层级
     * @return 连续部门负责人 Id
     */
    protected Set<Long> getMultiLevelDeptLeaderIds(List<Long> deptIds, Integer level) {
        Assert.isTrue(level > 0, "level 必须大于 0");
        if (CollUtil.isEmpty(deptIds)) {
            return new HashSet<>();
        }
        Set<Long> deptLeaderIds = new LinkedHashSet<>(); // 保证有序
        for (Long deptId : deptIds) {
            DeptRespDTO dept = deptApi.getDept(deptId);
            for (int i = 0; i < level; i++) {
                if (dept.getLeaderUserId() != null) {
                    deptLeaderIds.add(dept.getLeaderUserId());
                }
                DeptRespDTO parentDept = deptApi.getDept(dept.getParentId());
                if (parentDept == null) { // 找不到父级部门. 已经到了最高层级了
                    break;
                }
                dept = parentDept;
            }
        }
        return deptLeaderIds;
    }

    /**
     * 获取发起人的部门
     *
     * @param startUserId 发起人 Id
     */
    protected DeptRespDTO getStartUserDept(Long startUserId) {
        AdminUserRespDTO startUser = adminUserApi.getUser(startUserId);
        if (startUser.getDeptId() == null) { // 找不到部门
            return null;
        }
        return deptApi.getDept(startUser.getDeptId());
    }

}
