package ryd.checknm.dashboard.module.product.service.history;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.product.controller.admin.history.vo.ProductBrowseHistoryPageReqVO;
import ryd.checknm.dashboard.module.product.dal.dataobject.history.ProductBrowseHistoryDO;
import org.springframework.scheduling.annotation.Async;

import java.util.Collection;

/**
 * 商品浏览记录 Service 接口
 *
 * @author owen
 */
public interface ProductBrowseHistoryService {

    /**
     * 创建商品浏览记录
     *
     * @param userId 用户编号
     * @param spuId  SPU 编号
     */
    @Async
    void createBrowseHistory(Long userId, Long spuId);

    /**
     * 隐藏用户商品浏览记录
     *
     * @param userId 用户编号
     * @param spuId  SPU 编号
     */
    void hideUserBrowseHistory(Long userId, Collection<Long> spuId);

    /**
     * 获得商品浏览记录分页
     *
     * @param pageReqVO 分页查询
     * @return 商品浏览记录分页
     */
    PageResult<ProductBrowseHistoryDO> getBrowseHistoryPage(ProductBrowseHistoryPageReqVO pageReqVO);

}