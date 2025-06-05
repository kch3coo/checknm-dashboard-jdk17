package ryd.checknm.dashboard.module.pay.convert.wallet;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionRespVO;
import ryd.checknm.dashboard.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionRespVO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import ryd.checknm.dashboard.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWalletTransactionConvert {

    PayWalletTransactionConvert INSTANCE = Mappers.getMapper(PayWalletTransactionConvert.class);

    PageResult<PayWalletTransactionRespVO> convertPage2(PageResult<PayWalletTransactionDO> page);

    PayWalletTransactionDO convert(WalletTransactionCreateReqBO bean);

}
