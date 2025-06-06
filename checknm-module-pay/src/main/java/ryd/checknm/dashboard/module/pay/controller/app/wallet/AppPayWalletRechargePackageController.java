package ryd.checknm.dashboard.module.pay.controller.app.wallet;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.pay.controller.app.wallet.vo.recharge.AppPayWalletPackageRespVO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import ryd.checknm.dashboard.module.pay.service.wallet.PayWalletRechargePackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 钱包充值套餐")
@RestController
@RequestMapping("/pay/wallet-recharge-package")
@Validated
@Slf4j
public class AppPayWalletRechargePackageController {

    @Resource
    private PayWalletRechargePackageService walletRechargePackageService;

    @GetMapping("/list")
    @Operation(summary = "获得钱包充值套餐列表")
    public CommonResult<List<AppPayWalletPackageRespVO>> getWalletRechargePackageList() {
        List<PayWalletRechargePackageDO> list = walletRechargePackageService.getWalletRechargePackageList(
                CommonStatusEnum.ENABLE.getStatus());
        list.sort(Comparator.comparingInt(PayWalletRechargePackageDO::getPayPrice));
        return success(BeanUtils.toBean(list, AppPayWalletPackageRespVO.class));
    }

}
