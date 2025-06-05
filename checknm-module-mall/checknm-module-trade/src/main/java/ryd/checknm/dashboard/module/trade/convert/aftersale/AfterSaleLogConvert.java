package ryd.checknm.dashboard.module.trade.convert.aftersale;

import ryd.checknm.dashboard.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import ryd.checknm.dashboard.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AfterSaleLogConvert {

    AfterSaleLogConvert INSTANCE = Mappers.getMapper(AfterSaleLogConvert.class);

    AfterSaleLogDO convert(AfterSaleLogCreateReqBO bean);

}
