package ryd.checknm.dashboard.module.pay.controller.admin.wallet;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionPageReqVO;
import ryd.checknm.dashboard.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionRespVO;
import ryd.checknm.dashboard.module.pay.convert.wallet.PayWalletTransactionConvert;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import ryd.checknm.dashboard.module.pay.service.wallet.PayWalletTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 钱包余额明细")
@RestController
@RequestMapping("/pay/wallet-transaction")
@Validated
@Slf4j
public class PayWalletTransactionController {

    @Resource
    private PayWalletTransactionService payWalletTransactionService;

    @GetMapping("/page")
    @Operation(summary = "获得钱包流水分页")
    @PreAuthorize("@ss.hasPermission('pay:wallet:query')")
    public CommonResult<PageResult<PayWalletTransactionRespVO>> getWalletTransactionPage(
            @Valid PayWalletTransactionPageReqVO pageReqVO) {
        PageResult<PayWalletTransactionDO> result = payWalletTransactionService.getWalletTransactionPage(pageReqVO);
        return success(PayWalletTransactionConvert.INSTANCE.convertPage2(result));
    }

}
