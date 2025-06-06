package ryd.checknm.dashboard.module.statistics.service.pay;

import ryd.checknm.dashboard.module.pay.enums.wallet.PayWalletBizTypeEnum;
import ryd.checknm.dashboard.module.pay.enums.order.PayOrderStatusEnum;
import ryd.checknm.dashboard.module.pay.enums.refund.PayRefundStatusEnum;
import ryd.checknm.dashboard.module.statistics.dal.mysql.pay.PayWalletStatisticsMapper;
import ryd.checknm.dashboard.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import ryd.checknm.dashboard.module.statistics.service.trade.bo.WalletSummaryRespBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 钱包的统计 Service 实现类
 *
 * @author owen
 */
@Service
@Validated
public class PayWalletStatisticsServiceImpl implements PayWalletStatisticsService {

    @Resource
    private PayWalletStatisticsMapper payWalletStatisticsMapper;

    @Override
    public WalletSummaryRespBO getWalletSummary(LocalDateTime beginTime, LocalDateTime endTime) {
        WalletSummaryRespBO paySummary = payWalletStatisticsMapper.selectRechargeSummaryByPayTimeBetween(
                beginTime, endTime, true);
        WalletSummaryRespBO refundSummary = payWalletStatisticsMapper.selectRechargeSummaryByRefundTimeBetween(
                beginTime, endTime, PayRefundStatusEnum.SUCCESS.getStatus());
        Integer walletPayPrice = payWalletStatisticsMapper.selectPriceSummaryByBizTypeAndCreateTimeBetween(
                beginTime, endTime, PayWalletBizTypeEnum.PAYMENT.getType());
        // 拼接
        paySummary.setWalletPayPrice(walletPayPrice)
                .setRechargeRefundCount(refundSummary.getRechargeRefundCount())
                .setRechargeRefundPrice(refundSummary.getRechargeRefundPrice());
        return paySummary;
    }

    @Override
    public RechargeSummaryRespBO getUserRechargeSummary(LocalDateTime beginTime, LocalDateTime endTime) {
        return payWalletStatisticsMapper.selectRechargeSummaryGroupByWalletId(beginTime, endTime, true);
    }

    @Override
    public Integer getRechargePriceSummary() {
        return payWalletStatisticsMapper.selectRechargePriceSummary(Boolean.TRUE);
    }

}
