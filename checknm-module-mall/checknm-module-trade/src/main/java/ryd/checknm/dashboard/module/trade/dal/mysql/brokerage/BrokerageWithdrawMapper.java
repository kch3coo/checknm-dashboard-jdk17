package ryd.checknm.dashboard.module.trade.dal.mysql.brokerage;

import cn.hutool.core.bean.BeanUtil;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawPageReqVO;
import ryd.checknm.dashboard.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import ryd.checknm.dashboard.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 佣金提现 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BrokerageWithdrawMapper extends BaseMapperX<BrokerageWithdrawDO> {

    default PageResult<BrokerageWithdrawDO> selectPage(BrokerageWithdrawPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BrokerageWithdrawDO>()
                .eqIfPresent(BrokerageWithdrawDO::getUserId, reqVO.getUserId())
                .eqIfPresent(BrokerageWithdrawDO::getType, reqVO.getType())
                .likeIfPresent(BrokerageWithdrawDO::getUserName, reqVO.getUserName())
                .likeIfPresent(BrokerageWithdrawDO::getUserAccount, reqVO.getUserAccount())
                .likeIfPresent(BrokerageWithdrawDO::getBankName, reqVO.getBankName())
                .eqIfPresent(BrokerageWithdrawDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BrokerageWithdrawDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(BrokerageWithdrawDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer whereStatus, BrokerageWithdrawDO updateObj) {
        return update(updateObj, new LambdaUpdateWrapper<BrokerageWithdrawDO>()
                .eq(BrokerageWithdrawDO::getId, id)
                .eq(BrokerageWithdrawDO::getStatus, whereStatus));
    }

    default List<BrokerageWithdrawSummaryRespBO> selectCountAndSumPriceByUserIdAndStatus(Collection<Long> userIds,
                                                                                         Collection<Integer> status) {
        List<Map<String, Object>> list = selectMaps(new MPJLambdaWrapper<BrokerageWithdrawDO>()
                .select(BrokerageWithdrawDO::getUserId)
                .selectCount(BrokerageWithdrawDO::getId, BrokerageWithdrawSummaryRespBO::getCount)
                .selectSum(BrokerageWithdrawDO::getPrice)
                .in(BrokerageWithdrawDO::getUserId, userIds)
                .in(BrokerageWithdrawDO::getStatus, status)
                .groupBy(BrokerageWithdrawDO::getUserId));
        return BeanUtil.copyToList(list, BrokerageWithdrawSummaryRespBO.class);
        // selectJoinList有BUG，会与租户插件冲突：解析SQL时，发生异常 https://gitee.com/best_handsome/mybatis-plus-join/issues/I84GYW
//        return selectJoinList(UserWithdrawSummaryBO.class, new MPJLambdaWrapper<BrokerageWithdrawDO>()
//                .select(BrokerageWithdrawDO::getUserId)
//                    .selectCount(BrokerageWithdrawDO::getId, UserWithdrawSummaryBO::getCount)
//                .selectSum(BrokerageWithdrawDO::getPrice)
//                .in(BrokerageWithdrawDO::getUserId, userIds)
//                .eq(BrokerageWithdrawDO::getStatus, status)
//                .groupBy(BrokerageWithdrawDO::getUserId));
    }

}
