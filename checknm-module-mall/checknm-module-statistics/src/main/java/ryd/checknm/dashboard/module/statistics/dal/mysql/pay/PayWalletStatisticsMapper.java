package ryd.checknm.dashboard.module.statistics.dal.mysql.pay;

import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import ryd.checknm.dashboard.module.statistics.service.trade.bo.WalletSummaryRespBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 支付钱包的统计 Mapper
 *
 * @author owen
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface PayWalletStatisticsMapper extends BaseMapperX {

    WalletSummaryRespBO selectRechargeSummaryByPayTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                              @Param("endTime") LocalDateTime endTime,
                                                              @Param("payStatus") Boolean payStatus);

    WalletSummaryRespBO selectRechargeSummaryByRefundTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                                 @Param("endTime") LocalDateTime endTime,
                                                                 @Param("refundStatus") Integer refundStatus);

    Integer selectPriceSummaryByBizTypeAndCreateTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                            @Param("endTime") LocalDateTime endTime,
                                                            @Param("bizType") Integer bizType);

    RechargeSummaryRespBO selectRechargeSummaryGroupByWalletId(@Param("beginTime") LocalDateTime beginTime,
                                                               @Param("endTime") LocalDateTime endTime,
                                                               @Param("payStatus") Boolean payStatus);

    Integer selectRechargePriceSummary(@Param("payStatus") Boolean payStatus);

}
