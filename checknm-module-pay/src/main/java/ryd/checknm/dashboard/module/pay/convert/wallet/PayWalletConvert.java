package ryd.checknm.dashboard.module.pay.convert.wallet;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.pay.controller.admin.wallet.vo.wallet.PayWalletRespVO;
import ryd.checknm.dashboard.module.pay.controller.app.wallet.vo.wallet.AppPayWalletRespVO;
import ryd.checknm.dashboard.module.pay.dal.dataobject.wallet.PayWalletDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWalletConvert {

    PayWalletConvert INSTANCE = Mappers.getMapper(PayWalletConvert.class);

    AppPayWalletRespVO convert(PayWalletDO bean);

    PayWalletRespVO convert02(PayWalletDO bean);

    PageResult<PayWalletRespVO> convertPage(PageResult<PayWalletDO> page);

}
