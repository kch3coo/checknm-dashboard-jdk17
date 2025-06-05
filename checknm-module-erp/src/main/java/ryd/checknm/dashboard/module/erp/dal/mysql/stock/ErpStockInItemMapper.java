package ryd.checknm.dashboard.module.erp.dal.mysql.stock;

import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockInItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * ERP 其它入库单项 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ErpStockInItemMapper extends BaseMapperX<ErpStockInItemDO> {

    default List<ErpStockInItemDO> selectListByInId(Long inId) {
        return selectList(ErpStockInItemDO::getInId, inId);
    }

    default List<ErpStockInItemDO> selectListByInIds(Collection<Long> inIds) {
        return selectList(ErpStockInItemDO::getInId, inIds);
    }

    default int deleteByInId(Long inId) {
        return delete(ErpStockInItemDO::getInId, inId);
    }

}