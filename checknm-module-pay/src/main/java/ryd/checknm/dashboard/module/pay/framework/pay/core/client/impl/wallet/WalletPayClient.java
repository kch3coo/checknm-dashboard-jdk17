package ryd.checknm.dashboard.module.pay.framework.pay.core.client.impl.wallet;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import ryd.checknm.dashboard.framework.common.exception.ServiceException;
import ryd.checknm.dashboard.module.pay.enums.PayChannelEnum;
import ryd.checknm.dashboard.module.pay.enums.refund.PayRefundStatusEnum;
import ryd.checknm.dashboard.module.pay.enums.transfer.PayTransferStatusEnum;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.order.PayOrderRespDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.impl.AbstractPayClient;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.impl.NonePayClientConfig;
import ryd.checknm.dashboard.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.refund.PayRefundDO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.transfer.PayTransferDO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import ryd.checknm.dashboard.module.pay.enums.order.PayOrderStatusEnum;
import ryd.checknm.dashboard.module.pay.enums.wallet.PayWalletBizTypeEnum;
import ryd.checknm.dashboard.module.pay.service.order.PayOrderService;
import ryd.checknm.dashboard.module.pay.service.refund.PayRefundService;
import ryd.checknm.dashboard.module.pay.service.transfer.PayTransferService;
import ryd.checknm.dashboard.module.pay.service.wallet.PayWalletService;
import ryd.checknm.dashboard.module.pay.service.wallet.PayWalletTransactionService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static ryd.checknm.dashboard.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static ryd.checknm.dashboard.module.pay.enums.ErrorCodeConstants.PAY_ORDER_EXTENSION_NOT_FOUND;
import static ryd.checknm.dashboard.module.pay.enums.ErrorCodeConstants.REFUND_NOT_FOUND;

/**
 * 钱包支付的 PayClient 实现类
 *
 * @author jason
 */
@Slf4j
public class WalletPayClient extends AbstractPayClient<NonePayClientConfig> {

    public static final String WALLET_ID_KEY = "walletId";

    private PayWalletService wallService;
    private PayWalletTransactionService walletTransactionService;

    private PayOrderService orderService;
    private PayRefundService refundService;
    private PayTransferService transferService;

    public WalletPayClient(Long channelId, NonePayClientConfig config) {
        super(channelId, PayChannelEnum.WALLET.getCode(), config);
    }

    @Override
    protected void doInit() {
        if (wallService == null) {
            wallService = SpringUtil.getBean(PayWalletService.class);
        }
        if (walletTransactionService == null) {
            walletTransactionService = SpringUtil.getBean(PayWalletTransactionService.class);
        }
    }

    @Override
    @SuppressWarnings("PatternVariableCanBeUsed")
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        try {
            Long walletId = MapUtil.getLong(reqDTO.getChannelExtras(), WALLET_ID_KEY);
            Assert.notNull(walletId, "钱包编号");
            PayWalletTransactionDO transaction = wallService.orderPay(walletId,
                    reqDTO.getOutTradeNo(), reqDTO.getPrice());
            return PayOrderRespDTO.successOf(transaction.getNo(), transaction.getCreator(),
                    transaction.getCreateTime(),
                    reqDTO.getOutTradeNo(), transaction);
        } catch (Throwable ex) {
            log.error("[doUnifiedOrder][reqDTO({}) 异常]", reqDTO, ex);
            Integer errorCode = INTERNAL_SERVER_ERROR.getCode();
            String errorMsg = INTERNAL_SERVER_ERROR.getMsg();
            if (ex instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) ex;
                errorCode = serviceException.getCode();
                errorMsg = serviceException.getMessage();
            }
            return PayOrderRespDTO.closedOf(String.valueOf(errorCode), errorMsg,
                    reqDTO.getOutTradeNo(), "");
        }
    }

    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body, Map<String, String> headers) {
        throw new UnsupportedOperationException("钱包支付无支付回调");
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) {
        if (orderService == null) {
            orderService = SpringUtil.getBean(PayOrderService.class);
        }
        PayOrderExtensionDO orderExtension = orderService.getOrderExtensionByNo(outTradeNo);
        // 支付交易拓展单不存在， 返回关闭状态
        if (orderExtension == null) {
            return PayOrderRespDTO.closedOf(String.valueOf(PAY_ORDER_EXTENSION_NOT_FOUND.getCode()),
                    PAY_ORDER_EXTENSION_NOT_FOUND.getMsg(), outTradeNo, "");
        }
        // 关闭状态
        if (PayOrderStatusEnum.isClosed(orderExtension.getStatus())) {
            return PayOrderRespDTO.closedOf(orderExtension.getChannelErrorCode(),
                    orderExtension.getChannelErrorMsg(), outTradeNo, "");
        }
        // 成功状态
        if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) {
            PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransaction(
                    String.valueOf(orderExtension.getOrderId()), PayWalletBizTypeEnum.PAYMENT);
            Assert.notNull(walletTransaction, "支付单 {} 钱包流水不能为空", outTradeNo);
            return PayOrderRespDTO.successOf(walletTransaction.getNo(), walletTransaction.getCreator(),
                    walletTransaction.getCreateTime(), outTradeNo, walletTransaction);
        }
        // 其它状态为无效状态
        log.error("[doGetOrder] 支付单 {} 的状态不正确", outTradeNo);
        throw new IllegalStateException(String.format("支付单[%s] 状态不正确", outTradeNo));
    }

    @Override
    @SuppressWarnings("PatternVariableCanBeUsed")
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        try {
            PayWalletTransactionDO payWalletTransaction = wallService.orderRefund(reqDTO.getOutRefundNo(),
                    reqDTO.getRefundPrice(), reqDTO.getReason());
            return PayRefundRespDTO.successOf(payWalletTransaction.getNo(), payWalletTransaction.getCreateTime(),
                    reqDTO.getOutRefundNo(), payWalletTransaction);
        } catch (Throwable ex) {
            log.error("[doUnifiedRefund][reqDOT({}) 异常]", reqDTO, ex);
            Integer errorCode = INTERNAL_SERVER_ERROR.getCode();
            String errorMsg = INTERNAL_SERVER_ERROR.getMsg();
            if (ex instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) ex;
                errorCode =  serviceException.getCode();
                errorMsg = serviceException.getMessage();
            }
            return PayRefundRespDTO.failureOf(String.valueOf(errorCode), errorMsg,
                    reqDTO.getOutRefundNo(), "");
        }
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body, Map<String, String> headers) {
        throw new UnsupportedOperationException("钱包支付无退款回调");
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) {
        if (refundService == null) {
            refundService = SpringUtil.getBean(PayRefundService.class);
        }
        PayRefundDO payRefund = refundService.getRefundByNo(outRefundNo);
        // 支付退款单不存在， 返回退款失败状态
        if (payRefund == null) {
            return PayRefundRespDTO.failureOf(String.valueOf(REFUND_NOT_FOUND), REFUND_NOT_FOUND.getMsg(),
                    outRefundNo, "");
        }
        // 退款失败
        if (PayRefundStatusEnum.isFailure(payRefund.getStatus())) {
            return PayRefundRespDTO.failureOf(payRefund.getChannelErrorCode(), payRefund.getChannelErrorMsg(),
                    outRefundNo, "");
        }
        // 退款成功
        if (PayRefundStatusEnum.isSuccess(payRefund.getStatus())) {
            PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransaction(
                    String.valueOf(payRefund.getId()), PayWalletBizTypeEnum.PAYMENT_REFUND);
            Assert.notNull(walletTransaction, "支付退款单 {} 钱包流水不能为空", outRefundNo);
            return PayRefundRespDTO.successOf(walletTransaction.getNo(), walletTransaction.getCreateTime(),
                    outRefundNo, walletTransaction);
        }
        // 其它状态为无效状态
        log.error("[doGetRefund] 支付退款单 {} 的状态不正确", outRefundNo);
        throw new IllegalStateException(String.format("支付退款单[%s] 状态不正确", outRefundNo));
    }

    @Override
    @SuppressWarnings("PatternVariableCanBeUsed")
    public PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        try {
            Long walletId = Long.parseLong(reqDTO.getUserAccount());
            PayWalletTransactionDO transaction = wallService.addWalletBalance(walletId, String.valueOf(reqDTO.getOutTransferNo()),
                    PayWalletBizTypeEnum.TRANSFER, reqDTO.getPrice());
            return PayTransferRespDTO.successOf(transaction.getNo(), transaction.getCreateTime(),
                    reqDTO.getOutTransferNo(), transaction);
        } catch (Throwable ex) {
            log.error("[doUnifiedTransfer][reqDTO({}) 异常]", reqDTO, ex);
            Integer errorCode = INTERNAL_SERVER_ERROR.getCode();
            String errorMsg = INTERNAL_SERVER_ERROR.getMsg();
            if (ex instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) ex;
                errorCode = serviceException.getCode();
                errorMsg = serviceException.getMessage();
            }
            return PayTransferRespDTO.closedOf(String.valueOf(errorCode), errorMsg,
                    reqDTO.getOutTransferNo(), "");
        }
    }

    @Override
    protected PayTransferRespDTO doParseTransferNotify(Map<String, String> params, String body, Map<String, String> headers) {
        throw new UnsupportedOperationException("钱包支付无转账回调");
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo) {
        if (transferService == null) {
            transferService = SpringUtil.getBean(PayTransferService.class);
        }
        // 获取转账单
        PayTransferDO transfer = transferService.getTransferByNo(outTradeNo);
        // 转账单不存在，返回关闭状态
        if (transfer == null) {
            return PayTransferRespDTO.closedOf(String.valueOf(PAY_ORDER_EXTENSION_NOT_FOUND.getCode()),
                    PAY_ORDER_EXTENSION_NOT_FOUND.getMsg(), outTradeNo, "");
        }
        // 关闭状态
        if (PayTransferStatusEnum.isClosed(transfer.getStatus())) {
            return PayTransferRespDTO.closedOf(transfer.getChannelErrorCode(),
                    transfer.getChannelErrorMsg(), outTradeNo, "");
        }
        // 成功状态
        if (PayTransferStatusEnum.isSuccess(transfer.getStatus())) {
            PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransaction(
                    String.valueOf(transfer.getId()), PayWalletBizTypeEnum.TRANSFER);
            Assert.notNull(walletTransaction, "转账单 {} 钱包流水不能为空", outTradeNo);
            return PayTransferRespDTO.successOf(walletTransaction.getNo(), walletTransaction.getCreateTime(),
                    outTradeNo, walletTransaction);
        }
        // 处理中状态
        if (PayTransferStatusEnum.isProcessing(transfer.getStatus())) {
            return PayTransferRespDTO.processingOf(transfer.getChannelTransferNo(),
                    outTradeNo, transfer);
        }
        // 等待状态
        if (PayTransferStatusEnum.isWaiting(transfer.getStatus())) {
            return PayTransferRespDTO.waitingOf(transfer.getChannelTransferNo(),
                    outTradeNo, transfer);
        }
        // 其它状态为无效状态
        log.error("[doGetTransfer] 转账单 {} 的状态不正确", outTradeNo);
        throw new IllegalStateException(String.format("转账单[%s] 状态不正确", outTradeNo));
    }

}
