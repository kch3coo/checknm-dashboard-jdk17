package ryd.checknm.dashboard.module.pay.convert.refund;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils;
import ryd.checknm.dashboard.framework.common.util.collection.MapUtils;
import ryd.checknm.dashboard.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import ryd.checknm.dashboard.module.pay.controller.admin.refund.vo.PayRefundDetailsRespVO;
import ryd.checknm.dashboard.module.pay.controller.admin.refund.vo.PayRefundExcelVO;
import ryd.checknm.dashboard.module.pay.controller.admin.refund.vo.PayRefundPageItemRespVO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.app.PayAppDO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.order.PayOrderDO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.refund.PayRefundDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayRefundConvert {

    PayRefundConvert INSTANCE = Mappers.getMapper(PayRefundConvert.class);


    default PayRefundDetailsRespVO convert(PayRefundDO refund, PayAppDO app) {
        PayRefundDetailsRespVO respVO = convert(refund);
        if (app != null) {
            respVO.setAppName(app.getName());
        }
        return respVO;
    }
    PayRefundDetailsRespVO convert(PayRefundDO bean);
    PayRefundDetailsRespVO.Order convert(PayOrderDO bean);

    default PageResult<PayRefundPageItemRespVO> convertPage(PageResult<PayRefundDO> page, Map<Long, PayAppDO> appMap) {
        PageResult<PayRefundPageItemRespVO> result = convertPage(page);
        result.getList().forEach(order -> MapUtils.findAndThen(appMap, order.getAppId(), app -> order.setAppName(app.getName())));
        return result;
    }
    PageResult<PayRefundPageItemRespVO> convertPage(PageResult<PayRefundDO> page);

    PayRefundDO convert(PayRefundCreateReqDTO bean);

    default List<PayRefundExcelVO> convertList(List<PayRefundDO> list, Map<Long, PayAppDO> appMap) {
        return CollectionUtils.convertList(list, order -> {
            PayRefundExcelVO excelVO = convertExcel(order);
            MapUtils.findAndThen(appMap, order.getAppId(), app -> excelVO.setAppName(app.getName()));
            return excelVO;
        });
    }
    PayRefundExcelVO convertExcel(PayRefundDO bean);

}
