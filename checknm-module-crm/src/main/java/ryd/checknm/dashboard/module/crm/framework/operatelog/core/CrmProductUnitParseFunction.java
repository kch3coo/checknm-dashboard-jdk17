package ryd.checknm.dashboard.module.crm.framework.operatelog.core;

import cn.hutool.core.util.StrUtil;
import ryd.checknm.dashboard.framework.dict.core.DictFrameworkUtils;
import ryd.checknm.dashboard.module.crm.enums.DictTypeConstants;
import com.mzt.logapi.service.IParseFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 产品单位的 {@link IParseFunction} 实现类
 *
 * @author anhaohao
 */
@Component
@Slf4j
public class CrmProductUnitParseFunction implements IParseFunction {

    public static final String NAME = "getProductUnitName";

    @Override
    public boolean executeBefore() {
        return true; // 先转换值后对比
    }

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }
        return DictFrameworkUtils.parseDictDataLabel(DictTypeConstants.CRM_PRODUCT_UNIT, value.toString());
    }

}
