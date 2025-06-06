package ryd.checknm.dashboard.module.statistics.convert.member;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils;
import ryd.checknm.dashboard.framework.ip.core.Area;
import ryd.checknm.dashboard.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import ryd.checknm.dashboard.module.statistics.controller.admin.member.vo.MemberAnalyseDataRespVO;
import ryd.checknm.dashboard.module.statistics.controller.admin.member.vo.MemberAnalyseRespVO;
import ryd.checknm.dashboard.module.statistics.controller.admin.member.vo.MemberAreaStatisticsRespVO;
import ryd.checknm.dashboard.module.statistics.controller.admin.member.vo.MemberSummaryRespVO;
import ryd.checknm.dashboard.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import ryd.checknm.dashboard.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 会员统计 Convert
 *
 * @author owen
 */
@Mapper
public interface MemberStatisticsConvert {

    MemberStatisticsConvert INSTANCE = Mappers.getMapper(MemberStatisticsConvert.class);

    default List<MemberAreaStatisticsRespVO> convertList(List<Area> areaList,
                                                         Map<Integer, Integer> userCountMap,
                                                         Map<Integer, MemberAreaStatisticsRespBO> orderMap) {
        return CollectionUtils.convertList(areaList, area -> {
            MemberAreaStatisticsRespBO orderVo = Optional.ofNullable(orderMap.get(area.getId()))
                    .orElseGet(MemberAreaStatisticsRespBO::new);
            return new MemberAreaStatisticsRespVO()
                    .setAreaId(area.getId()).setAreaName(area.getName())
                    .setUserCount(MapUtil.getInt(userCountMap, area.getId(), 0))
                    .setOrderCreateUserCount(ObjUtil.defaultIfNull(orderVo.getOrderCreateUserCount(), 0))
                    .setOrderPayUserCount(ObjUtil.defaultIfNull(orderVo.getOrderPayUserCount(), 0))
                    .setOrderPayPrice(ObjUtil.defaultIfNull(orderVo.getOrderPayPrice(), 0));
        });
    }

    MemberSummaryRespVO convert(RechargeSummaryRespBO rechargeSummary, Integer expensePrice, Integer userCount);

    MemberAnalyseRespVO convert(Integer visitUserCount, Integer orderUserCount, Integer payUserCount, int atv,
                                DataComparisonRespVO<MemberAnalyseDataRespVO> comparison);

}
