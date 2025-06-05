package ryd.checknm.dashboard.module.system.api.tenant;

import ryd.checknm.dashboard.framework.common.biz.system.tenant.TenantCommonApi;
import ryd.checknm.dashboard.module.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class TenantApiImpl implements TenantCommonApi {

    @Resource
    private TenantService tenantService;

    @Override
    public List<Long> getTenantIdList() {
        return tenantService.getTenantIdList();
    }

    @Override
    public void validateTenant(Long id) {
        tenantService.validTenant(id);
    }

}
