package ryd.checknm.dashboard.module.bpm.controller.admin.task;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.collection.MapUtils;
import ryd.checknm.dashboard.framework.common.util.date.DateUtils;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.bpm.controller.admin.base.user.UserSimpleBaseVO;
import ryd.checknm.dashboard.module.bpm.controller.admin.task.vo.cc.BpmProcessInstanceCopyRespVO;
import ryd.checknm.dashboard.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCopyPageReqVO;
import ryd.checknm.dashboard.module.bpm.dal.dataobject.definition.BpmProcessDefinitionInfoDO;
import ryd.checknm.dashboard.module.bpm.dal.dataobject.task.BpmProcessInstanceCopyDO;
import ryd.checknm.dashboard.module.bpm.framework.flowable.core.util.FlowableUtils;
import ryd.checknm.dashboard.module.bpm.service.definition.BpmProcessDefinitionService;
import ryd.checknm.dashboard.module.bpm.service.task.BpmProcessInstanceCopyService;
import ryd.checknm.dashboard.module.bpm.service.task.BpmProcessInstanceService;
import ryd.checknm.dashboard.module.system.api.user.AdminUserApi;
import ryd.checknm.dashboard.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Stream;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.*;
import static ryd.checknm.dashboard.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 流程实例抄送")
@RestController
@RequestMapping("/bpm/process-instance/copy")
@Validated
public class BpmProcessInstanceCopyController {

    @Resource
    private BpmProcessInstanceCopyService processInstanceCopyService;
    @Resource
    private BpmProcessInstanceService processInstanceService;
    @Resource
    private BpmProcessDefinitionService processDefinitionService;

    @Resource
    private AdminUserApi adminUserApi;

    @GetMapping("/page")
    @Operation(summary = "获得抄送流程分页列表")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance-cc:query')")
    public CommonResult<PageResult<BpmProcessInstanceCopyRespVO>> getProcessInstanceCopyPage(
            @Valid BpmProcessInstanceCopyPageReqVO pageReqVO) {
        PageResult<BpmProcessInstanceCopyDO> pageResult = processInstanceCopyService.getProcessInstanceCopyPage(
                getLoginUserId(), pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }

        // 拼接返回
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getProcessInstanceId));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(pageResult.getList(),
                copy -> Stream.of(copy.getStartUserId(), Long.parseLong(copy.getCreator()))));
        Map<String, BpmProcessDefinitionInfoDO> processDefinitionInfoMap = processDefinitionService.getProcessDefinitionInfoMap(
                convertSet(pageResult.getList(), BpmProcessInstanceCopyDO::getProcessDefinitionId));
        return success(convertPage(pageResult, copy -> {
            BpmProcessInstanceCopyRespVO copyVO = BeanUtils.toBean(copy, BpmProcessInstanceCopyRespVO.class);
            MapUtils.findAndThen(userMap, Long.valueOf(copy.getCreator()),
                    user -> copyVO.setStartUser(BeanUtils.toBean(user, UserSimpleBaseVO.class)));
            MapUtils.findAndThen(userMap, copy.getStartUserId(),
                    user -> copyVO.setCreateUser(BeanUtils.toBean(user, UserSimpleBaseVO.class)));
            MapUtils.findAndThen(processInstanceMap, copyVO.getProcessInstanceId(),
                    processInstance -> {
                        copyVO.setSummary(FlowableUtils.getSummary(
                                processDefinitionInfoMap.get(processInstance.getProcessDefinitionId()),
                                processInstance.getProcessVariables()));
                        copyVO.setProcessInstanceStartTime(DateUtils.of(processInstance.getStartTime()));
                    });
            return copyVO;
        }));
    }

}
