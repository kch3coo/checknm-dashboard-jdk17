package ryd.checknm.dashboard.module.crm.controller.admin.customer;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.crm.controller.admin.customer.vo.poolconfig.CrmCustomerPoolConfigRespVO;
import ryd.checknm.dashboard.module.crm.controller.admin.customer.vo.poolconfig.CrmCustomerPoolConfigSaveReqVO;
import ryd.checknm.dashboard.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import ryd.checknm.dashboard.module.crm.service.customer.CrmCustomerPoolConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - CRM 客户公海配置")
@RestController
@RequestMapping("/crm/customer-pool-config")
@Validated
public class CrmCustomerPoolConfigController {

    @Resource
    private CrmCustomerPoolConfigService customerPoolConfigService;

    @GetMapping("/get")
    @Operation(summary = "获取客户公海规则设置")
    @PreAuthorize("@ss.hasPermission('crm:customer-pool-config:query')")
    public CommonResult<CrmCustomerPoolConfigRespVO> getCustomerPoolConfig() {
        CrmCustomerPoolConfigDO poolConfig = customerPoolConfigService.getCustomerPoolConfig();
        return success(BeanUtils.toBean(poolConfig, CrmCustomerPoolConfigRespVO.class));
    }

    @PutMapping("/save")
    @Operation(summary = "更新客户公海规则设置")
    @PreAuthorize("@ss.hasPermission('crm:customer-pool-config:update')")
    public CommonResult<Boolean> saveCustomerPoolConfig(@Valid @RequestBody CrmCustomerPoolConfigSaveReqVO updateReqVO) {
        customerPoolConfigService.saveCustomerPoolConfig(updateReqVO);
        return success(true);
    }

}
