package ryd.checknm.dashboard.module.trade.convert.order;

import ryd.checknm.dashboard.module.trade.dal.dataobject.order.TradeOrderLogDO;
import ryd.checknm.dashboard.module.trade.service.order.bo.TradeOrderLogCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradeOrderLogConvert {

    TradeOrderLogConvert INSTANCE = Mappers.getMapper(TradeOrderLogConvert.class);

    TradeOrderLogDO convert(TradeOrderLogCreateReqBO bean);

}
