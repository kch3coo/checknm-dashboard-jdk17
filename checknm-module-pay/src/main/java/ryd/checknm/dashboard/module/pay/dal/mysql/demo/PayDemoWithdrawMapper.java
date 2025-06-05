package ryd.checknm.dashboard.module.pay.dal.mysql.demo;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.pay.dal.dataobject.demo.PayDemoWithdrawDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDemoWithdrawMapper extends BaseMapperX<PayDemoWithdrawDO> {

    default  PageResult<PayDemoWithdrawDO> selectPage(PageParam pageParam){
        return selectPage(pageParam, new LambdaQueryWrapperX<PayDemoWithdrawDO>()
                .orderByDesc(PayDemoWithdrawDO::getId));
    }

    default int updateByIdAndStatus(Long id, Integer whereStatus, PayDemoWithdrawDO updateObj) {
        return update(updateObj, new LambdaQueryWrapperX<PayDemoWithdrawDO>()
                .eq(PayDemoWithdrawDO::getId, id)
                .eq(PayDemoWithdrawDO::getStatus, whereStatus));
    }

}