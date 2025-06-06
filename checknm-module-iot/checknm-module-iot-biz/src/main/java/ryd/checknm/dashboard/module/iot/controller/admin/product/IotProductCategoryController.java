package ryd.checknm.dashboard.module.iot.controller.admin.product;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.iot.controller.admin.product.vo.category.IotProductCategoryPageReqVO;
import ryd.checknm.dashboard.module.iot.controller.admin.product.vo.category.IotProductCategoryRespVO;
import ryd.checknm.dashboard.module.iot.controller.admin.product.vo.category.IotProductCategorySaveReqVO;
import ryd.checknm.dashboard.module.iot.dal.dataobject.product.IotProductCategoryDO;
import ryd.checknm.dashboard.module.iot.service.product.IotProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - IoT 产品分类")
@RestController
@RequestMapping("/iot/product-category")
@Validated
public class IotProductCategoryController {

    @Resource
    private IotProductCategoryService productCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建产品分类")
    @PreAuthorize("@ss.hasPermission('iot:product-category:create')")
    public CommonResult<Long> createProductCategory(@Valid @RequestBody IotProductCategorySaveReqVO createReqVO) {
        return success(productCategoryService.createProductCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品分类")
    @PreAuthorize("@ss.hasPermission('iot:product-category:update')")
    public CommonResult<Boolean> updateProductCategory(@Valid @RequestBody IotProductCategorySaveReqVO updateReqVO) {
        productCategoryService.updateProductCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:product-category:query')")
    public CommonResult<IotProductCategoryRespVO> getProductCategory(@RequestParam("id") Long id) {
        IotProductCategoryDO productCategory = productCategoryService.getProductCategory(id);
        return success(BeanUtils.toBean(productCategory, IotProductCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品分类分页")
    @PreAuthorize("@ss.hasPermission('iot:product-category:query')")
    public CommonResult<PageResult<IotProductCategoryRespVO>> getProductCategoryPage(@Valid IotProductCategoryPageReqVO pageReqVO) {
        PageResult<IotProductCategoryDO> pageResult = productCategoryService.getProductCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IotProductCategoryRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得所有产品分类列表")
    @PreAuthorize("@ss.hasPermission('iot:product-category:query')")
    public CommonResult<List<IotProductCategoryRespVO>> getSimpleProductCategoryList() {
        List<IotProductCategoryDO> list = productCategoryService.getProductCategoryListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        return success(convertList(list, category ->
                new IotProductCategoryRespVO().setId(category.getId()).setName(category.getName())));
    }

}