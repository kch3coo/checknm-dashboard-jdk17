package ryd.checknm.dashboard.module.erp.service.purchase;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.erp.controller.admin.purchase.vo.in.ErpPurchaseInPageReqVO;
import ryd.checknm.dashboard.module.erp.controller.admin.purchase.vo.in.ErpPurchaseInSaveReqVO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.purchase.ErpPurchaseInDO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.purchase.ErpPurchaseInItemDO;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * ERP 采购入库 Service 接口
 *
 * @author 芋道源码
 */
public interface ErpPurchaseInService {

    /**
     * 创建采购入库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPurchaseIn(@Valid ErpPurchaseInSaveReqVO createReqVO);

    /**
     * 更新采购入库
     *
     * @param updateReqVO 更新信息
     */
    void updatePurchaseIn(@Valid ErpPurchaseInSaveReqVO updateReqVO);

    /**
     * 更新采购入库的状态
     *
     * @param id 编号
     * @param status 状态
     */
    void updatePurchaseInStatus(Long id, Integer status);

    /**
     * 更新采购入库的付款金额
     *
     * @param id 编号
     * @param paymentPrice 付款金额
     */
    void updatePurchaseInPaymentPrice(Long id, BigDecimal paymentPrice);

    /**
     * 删除采购入库
     *
     * @param ids 编号数组
     */
    void deletePurchaseIn(List<Long> ids);

    /**
     * 获得采购入库
     *
     * @param id 编号
     * @return 采购入库
     */
    ErpPurchaseInDO getPurchaseIn(Long id);

    /**
     * 校验采购入库，已经审核通过
     *
     * @param id 编号
     * @return 采购入库
     */
    ErpPurchaseInDO validatePurchaseIn(Long id);

    /**
     * 获得采购入库分页
     *
     * @param pageReqVO 分页查询
     * @return 采购入库分页
     */
    PageResult<ErpPurchaseInDO> getPurchaseInPage(ErpPurchaseInPageReqVO pageReqVO);

    // ==================== 采购入库项 ====================

    /**
     * 获得采购入库项列表
     *
     * @param inId 采购入库编号
     * @return 采购入库项列表
     */
    List<ErpPurchaseInItemDO> getPurchaseInItemListByInId(Long inId);

    /**
     * 获得采购入库项 List
     *
     * @param inIds 采购入库编号数组
     * @return 采购入库项 List
     */
    List<ErpPurchaseInItemDO> getPurchaseInItemListByInIds(Collection<Long> inIds);

}