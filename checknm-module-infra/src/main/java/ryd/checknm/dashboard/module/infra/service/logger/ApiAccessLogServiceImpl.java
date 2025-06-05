package ryd.checknm.dashboard.module.infra.service.logger;

import ryd.checknm.dashboard.framework.common.biz.infra.logger.dto.ApiAccessLogCreateReqDTO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.framework.common.util.string.StrUtils;
import ryd.checknm.dashboard.framework.tenant.core.context.TenantContextHolder;
import ryd.checknm.dashboard.framework.tenant.core.util.TenantUtils;
import ryd.checknm.dashboard.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import ryd.checknm.dashboard.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import ryd.checknm.dashboard.module.infra.dal.mysql.logger.ApiAccessLogMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

import static ryd.checknm.dashboard.module.infra.dal.dataobject.logger.ApiAccessLogDO.REQUEST_PARAMS_MAX_LENGTH;
import static ryd.checknm.dashboard.module.infra.dal.dataobject.logger.ApiAccessLogDO.RESULT_MSG_MAX_LENGTH;

/**
 * API 访问日志 Service 实现类
 *
 * @author 芋道源码
 */
@Slf4j
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = BeanUtils.toBean(createDTO, ApiAccessLogDO.class);
        apiAccessLog.setRequestParams(StrUtils.maxLength(apiAccessLog.getRequestParams(), REQUEST_PARAMS_MAX_LENGTH));
        apiAccessLog.setResultMsg(StrUtils.maxLength(apiAccessLog.getResultMsg(), RESULT_MSG_MAX_LENGTH));
        if (TenantContextHolder.getTenantId() != null) {
            apiAccessLogMapper.insert(apiAccessLog);
        } else {
            // 极端情况下，上下文中没有租户时，此时忽略租户上下文，避免插入失败！
            TenantUtils.executeIgnore(() -> apiAccessLogMapper.insert(apiAccessLog));
        }
    }

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanAccessLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // 循环删除，直到没有满足条件的数据
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiAccessLogMapper.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // 达到删除预期条数，说明到底了
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}
