package ryd.checknm.dashboard.module.trade.service.aftersale;

import ryd.checknm.dashboard.module.trade.convert.aftersale.AfterSaleLogConvert;
import ryd.checknm.dashboard.module.trade.dal.dataobject.aftersale.AfterSaleLogDO;
import ryd.checknm.dashboard.module.trade.dal.mysql.aftersale.AfterSaleLogMapper;
import ryd.checknm.dashboard.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 交易售后日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class AfterSaleLogServiceImpl implements AfterSaleLogService {

    @Resource
    private AfterSaleLogMapper afterSaleLogMapper;

    @Override
    public void createAfterSaleLog(AfterSaleLogCreateReqBO createReqBO) {
        AfterSaleLogDO afterSaleLog = AfterSaleLogConvert.INSTANCE.convert(createReqBO);
        afterSaleLogMapper.insert(afterSaleLog);
    }

    @Override
    public List<AfterSaleLogDO> getAfterSaleLogList(Long afterSaleId) {
        return afterSaleLogMapper.selectListByAfterSaleId(afterSaleId);
    }

}
