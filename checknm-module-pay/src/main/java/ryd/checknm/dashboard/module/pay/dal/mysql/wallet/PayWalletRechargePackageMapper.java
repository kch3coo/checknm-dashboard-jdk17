package ryd.checknm.dashboard.module.pay.dal.mysql.wallet;


import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackagePageReqVO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayWalletRechargePackageMapper extends BaseMapperX<PayWalletRechargePackageDO> {

    default PageResult<PayWalletRechargePackageDO> selectPage(WalletRechargePackagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayWalletRechargePackageDO>()
                .likeIfPresent(PayWalletRechargePackageDO::getName, reqVO.getName())
                .eqIfPresent(PayWalletRechargePackageDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PayWalletRechargePackageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayWalletRechargePackageDO::getPayPrice));
    }

    default PayWalletRechargePackageDO selectByName(String name) {
        return selectOne(PayWalletRechargePackageDO::getName, name);
    }

    default List<PayWalletRechargePackageDO> selectListByStatus(Integer status) {
        return selectList(PayWalletRechargePackageDO::getStatus, status);
    }

}
