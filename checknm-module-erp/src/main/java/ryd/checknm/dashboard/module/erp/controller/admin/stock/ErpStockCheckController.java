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
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.check.ErpStockCheckPageReqVO;
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.check.ErpStockCheckRespVO;
import ryd.checknm.dashboard.module.erp.controller.admin.stock.vo.check.ErpStockCheckSaveReqVO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockCheckDO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.stock.ErpStockCheckItemDO;
import ryd.checknm.dashboard.module.erp.service.product.ErpProductService;
import ryd.checknm.dashboard.module.erp.service.stock.ErpStockCheckService;
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
import java.util.List;
import java.util.Map;

import static ryd.checknm.dashboard.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertMultiMap;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - ERP 库存调拨单")
@RestController
@RequestMapping("/erp/stock-check")
@Validated
public class ErpStockCheckController {

    @Resource
    private ErpStockCheckService stockCheckService;
    @Resource
    private ErpProductService productService;

    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "创建库存调拨单")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:create')")
    public CommonResult<Long> createStockCheck(@Valid @RequestBody ErpStockCheckSaveReqVO createReqVO) {
        return success(stockCheckService.createStockCheck(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库存调拨单")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:update')")
    public CommonResult<Boolean> updateStockCheck(@Valid @RequestBody ErpStockCheckSaveReqVO updateReqVO) {
        stockCheckService.updateStockCheck(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新库存调拨单的状态")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:update-status')")
    public CommonResult<Boolean> updateStockCheckStatus(@RequestParam("id") Long id,
                                                     @RequestParam("status") Integer status) {
        stockCheckService.updateStockCheckStatus(id, status);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除库存调拨单")
    @Parameter(name = "ids", description = "编号数组", required = true)
    @PreAuthorize("@ss.hasPermission('erp:stock-check:delete')")
    public CommonResult<Boolean> deleteStockCheck(@RequestParam("ids") List<Long> ids) {
        stockCheckService.deleteStockCheck(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得库存调拨单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:query')")
    public CommonResult<ErpStockCheckRespVO> getStockCheck(@RequestParam("id") Long id) {
        ErpStockCheckDO stockCheck = stockCheckService.getStockCheck(id);
        if (stockCheck == null) {
            return success(null);
        }
        List<ErpStockCheckItemDO> stockCheckItemList = stockCheckService.getStockCheckItemListByCheckId(id);
        Map<Long, ErpProductRespVO> productMap = productService.getProductVOMap(
                convertSet(stockCheckItemList, ErpStockCheckItemDO::getProductId));
        return success(BeanUtils.toBean(stockCheck, ErpStockCheckRespVO.class, stockCheckVO ->
                stockCheckVO.setItems(BeanUtils.toBean(stockCheckItemList, ErpStockCheckRespVO.Item.class, item ->
                        MapUtils.findAndThen(productMap, item.getProductId(), product -> item.setProductName(product.getName())
                                .setProductBarCode(product.getBarCode()).setProductUnitName(product.getUnitName()))))));
    }

    @GetMapping("/page")
    @Operation(summary = "获得库存调拨单分页")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:query')")
    public CommonResult<PageResult<ErpStockCheckRespVO>> getStockCheckPage(@Valid ErpStockCheckPageReqVO pageReqVO) {
        PageResult<ErpStockCheckDO> pageResult = stockCheckService.getStockCheckPage(pageReqVO);
        return success(buildStockCheckVOPageResult(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库存调拨单 Excel")
    @PreAuthorize("@ss.hasPermission('erp:stock-check:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStockCheckExcel(@Valid ErpStockCheckPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErpStockCheckRespVO> list = buildStockCheckVOPageResult(stockCheckService.getStockCheckPage(pageReqVO)).getList();
        // 导出 Excel
        ExcelUtils.write(response, "库存调拨单.xls", "数据", ErpStockCheckRespVO.class, list);
    }

    private PageResult<ErpStockCheckRespVO> buildStockCheckVOPageResult(PageResult<ErpStockCheckDO> pageResult) {
        if (CollUtil.isEmpty(pageResult.getList())) {
            return PageResult.empty(pageResult.getTotal());
        }
        // 1.1 盘点项
        List<ErpStockCheckItemDO> stockCheckItemList = stockCheckService.getStockCheckItemListByCheckIds(
                convertSet(pageResult.getList(), ErpStockCheckDO::getId));
        Map<Long, List<ErpStockCheckItemDO>> stockCheckItemMap = convertMultiMap(stockCheckItemList, ErpStockCheckItemDO::getCheckId);
        // 1.2 产品信息
        Map<Long, ErpProductRespVO> productMap = productService.getProductVOMap(
                convertSet(stockCheckItemList, ErpStockCheckItemDO::getProductId));
        // 1.3 管理员信息
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(pageResult.getList(), stockCheck -> Long.parseLong(stockCheck.getCreator())));
        // 2. 开始拼接
        return BeanUtils.toBean(pageResult, ErpStockCheckRespVO.class, stockCheck -> {
            stockCheck.setItems(BeanUtils.toBean(stockCheckItemMap.get(stockCheck.getId()), ErpStockCheckRespVO.Item.class,
                    item -> MapUtils.findAndThen(productMap, item.getProductId(), product -> item.setProductName(product.getName())
                            .setProductBarCode(product.getBarCode()).setProductUnitName(product.getUnitName()))));
            stockCheck.setProductNames(CollUtil.join(stockCheck.getItems(), "，", ErpStockCheckRespVO.Item::getProductName));
            MapUtils.findAndThen(userMap, Long.parseLong(stockCheck.getCreator()), user -> stockCheck.setCreatorName(user.getNickname()));
        });
    }

}