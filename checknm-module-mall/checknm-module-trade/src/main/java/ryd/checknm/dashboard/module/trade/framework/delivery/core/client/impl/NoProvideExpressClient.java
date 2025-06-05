package ryd.checknm.dashboard.module.trade.framework.delivery.core.client.impl;

import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.ExpressClient;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;

import java.util.List;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.module.trade.enums.ErrorCodeConstants.EXPRESS_CLIENT_NOT_PROVIDE;

/**
 * 未实现的快递客户端，用来提醒用户需要接入快递服务商，
 *
 * @author jason
 */
public class NoProvideExpressClient implements ExpressClient {

    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(ExpressTrackQueryReqDTO reqDTO) {
        throw exception(EXPRESS_CLIENT_NOT_PROVIDE);
    }

}
