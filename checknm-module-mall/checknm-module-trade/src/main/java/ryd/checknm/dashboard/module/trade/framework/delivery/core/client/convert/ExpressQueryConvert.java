package ryd.checknm.dashboard.module.trade.framework.delivery.core.client.convert;

import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.kd100.Kd100ExpressQueryReqDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.kd100.Kd100ExpressQueryRespDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.kdniao.KdNiaoExpressQueryReqDTO;
import ryd.checknm.dashboard.module.trade.framework.delivery.core.client.dto.kdniao.KdNiaoExpressQueryRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ExpressQueryConvert {

    ExpressQueryConvert INSTANCE = Mappers.getMapper(ExpressQueryConvert.class);

    List<ExpressTrackRespDTO> convertList(List<KdNiaoExpressQueryRespDTO.ExpressTrack> list);
    @Mapping(source = "acceptTime", target = "time")
    @Mapping(source = "acceptStation", target = "content")
    ExpressTrackRespDTO convert(KdNiaoExpressQueryRespDTO.ExpressTrack track);

    List<ExpressTrackRespDTO> convertList2(List<Kd100ExpressQueryRespDTO.ExpressTrack> list);
    @Mapping(source = "context", target = "content")
    ExpressTrackRespDTO convert(Kd100ExpressQueryRespDTO.ExpressTrack track);

    KdNiaoExpressQueryReqDTO convert(ExpressTrackQueryReqDTO dto);

    Kd100ExpressQueryReqDTO convert2(ExpressTrackQueryReqDTO dto);

}
