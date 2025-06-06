package ryd.checknm.dashboard.module.erp.controller.admin.stock;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.apilog.core.annotation.ApiAccessLog;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.collection.MapUtils;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.framework.excel.core.util.ExcelUtils;
import ryd.checknm.dashboard.module.erp.controller.admin.product.vo.product.ErpProductRespVO;
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.in.ErpStockInPageReqVO;
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.in.ErpStockInRespVO;
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.in.ErpStockInSaveReqVO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.purchase.ErpSupplierDO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockDO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockInDO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockInItemDO;
import ryd.checknm.dashboard.module.erp.service.product.ErpProductService;
import ryd.checknm.dashboard.module.erp.service.purchase.ErpSupplierService;
import ryd.checknm.dashboard.module.erp.service.stock.ErpStockInService;
import ryd.checknm.dashboard.module.erp.service.stock.ErpStockService;
import ryd.checknm.dashboard.module.system.api.user.AdminUserApi;
import ryd.checknm.dashboard.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static ryd.checknm.dashboard.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertMultiMap;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - ERP 其它入库单")
@RestController
@RequestMapping("/erp/stock-in")
@Validated
public class ErpStockInController {

    @Resource
    private ErpStockInService stockInService;
    @Resource
    private ErpStockService stockService;
    @Resource
    private ErpProductService productService;
    @Resource
    private ErpSupplierService supplierService;

    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "创建其它入库单")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:create')")
    public CommonResult<Long> createStockIn(@Valid @RequestBody ErpStockInSaveReqVO createReqVO) {
        return success(stockInService.createStockIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新其它入库单")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:update')")
    public CommonResult<Boolean> updateStockIn(@Valid @RequestBody ErpStockInSaveReqVO updateReqVO) {
        stockInService.updateStockIn(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新其它入库单的状态")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:update-status')")
    public CommonResult<Boolean> updateStockInStatus(@RequestParam("id") Long id,
                                                     @RequestParam("status") Integer status) {
        stockInService.updateStockInStatus(id, status);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除其它入库单")
    @Parameter(name = "ids", description = "编号数组", required = true)
    @PreAuthorize("@ss.hasPermission('erp:stock-in:delete')")
    public CommonResult<Boolean> deleteStockIn(@RequestParam("ids") List<Long> ids) {
        stockInService.deleteStockIn(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得其它入库单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:query')")
    public CommonResult<ErpStockInRespVO> getStockIn(@RequestParam("id") Long id) {
        ErpStockInDO stockIn = stockInService.getStockIn(id);
        if (stockIn == null) {
            return success(null);
        }
        List<ErpStockInItemDO> stockInItemList = stockInService.getStockInItemListByInId(id);
        Map<Long, ErpProductRespVO> productMap = productService.getProductVOMap(
                convertSet(stockInItemList, ErpStockInItemDO::getProductId));
        return success(BeanUtils.toBean(stockIn, ErpStockInRespVO.class, stockInVO ->
                stockInVO.setItems(BeanUtils.toBean(stockInItemList, ErpStockInRespVO.Item.class, item -> {
                    ErpStockDO stock = stockService.getStock(item.getProductId(), item.getWarehouseId());
                    item.setStockCount(stock != null ? stock.getCount() : BigDecimal.ZERO);
                    MapUtils.findAndThen(productMap, item.getProductId(), product -> item.setProductName(product.getName())
                            .setProductBarCode(product.getBarCode()).setProductUnitName(product.getUnitName()));
                }))));
    }

    @GetMapping("/page")
    @Operation(summary = "获得其它入库单分页")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:query')")
    public CommonResult<PageResult<ErpStockInRespVO>> getStockInPage(@Valid ErpStockInPageReqVO pageReqVO) {
        PageResult<ErpStockInDO> pageResult = stockInService.getStockInPage(pageReqVO);
        return success(buildStockInVOPageResult(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出其它入库单 Excel")
    @PreAuthorize("@ss.hasPermission('erp:stock-in:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStockInExcel(@Valid ErpStockInPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErpStockInRespVO> list = buildStockInVOPageResult(stockInService.getStockInPage(pageReqVO)).getList();
        // 导出 Excel
        ExcelUtils.write(response, "其它入库单.xls", "数据", ErpStockInRespVO.class, list);
    }

    private PageResult<ErpStockInRespVO> buildStockInVOPageResult(PageResult<ErpStockInDO> pageResult) {
        if (CollUtil.isEmpty(pageResult.getList())) {
            return PageResult.empty(pageResult.getTotal());
        }
        // 1.1 入库项
        List<ErpStockInItemDO> stockInItemList = stockInService.getStockInItemListByInIds(
                convertSet(pageResult.getList(), ErpStockInDO::getId));
        Map<Long, List<ErpStockInItemDO>> stockInItemMap = convertMultiMap(stockInItemList, ErpStockInItemDO::getInId);
        // 1.2 产品信息
        Map<Long, ErpProductRespVO> productMap = productService.getProductVOMap(
                convertSet(stockInItemList, ErpStockInItemDO::getProductId));
        // 1.3 供应商信息
        Map<Long, ErpSupplierDO> supplierMap = supplierService.getSupplierMap(
                convertSet(pageResult.getList(), ErpStockInDO::getSupplierId));
        // 1.4 管理员信息
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(pageResult.getList(), stockIn -> Long.parseLong(stockIn.getCreator())));
        // 2. 开始拼接
        return BeanUtils.toBean(pageResult, ErpStockInRespVO.class, stockIn -> {
            stockIn.setItems(BeanUtils.toBean(stockInItemMap.get(stockIn.getId()), ErpStockInRespVO.Item.class,
                    item -> MapUtils.findAndThen(productMap, item.getProductId(), product -> item.setProductName(product.getName())
                            .setProductBarCode(product.getBarCode()).setProductUnitName(product.getUnitName()))));
            stockIn.setProductNames(CollUtil.join(stockIn.getItems(), "，", ErpStockInRespVO.Item::getProductName));
            MapUtils.findAndThen(supplierMap, stockIn.getSupplierId(), supplier -> stockIn.setSupplierName(supplier.getName()));
            MapUtils.findAndThen(userMap, Long.parseLong(stockIn.getCreator()), user -> stockIn.setCreatorName(user.getNickname()));
        });
    }

}