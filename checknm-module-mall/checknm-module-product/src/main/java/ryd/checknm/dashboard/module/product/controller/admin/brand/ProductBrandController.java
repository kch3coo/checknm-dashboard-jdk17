package ryd.checknm.dashboard.module.product.controller.admin.brand;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.product.controller.admin.brand.vo.*;
import ryd.checknm.dashboard.module.product.convert.brand.ProductBrandConvert;
import ryd.checknm.dashboard.module.product.dal.dataobject.brand.ProductBrandDO;
import ryd.checknm.dashboard.module.product.service.brand.ProductBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 商品品牌")
@RestController
@RequestMapping("/product/brand")
@Validated
public class ProductBrandController {

    @Resource
    private ProductBrandService brandService;

    @PostMapping("/create")
    @Operation(summary = "创建品牌")
    @PreAuthorize("@ss.hasPermission('product:brand:create')")
    public CommonResult<Long> createBrand(@Valid @RequestBody ProductBrandCreateReqVO createReqVO) {
        return success(brandService.createBrand(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新品牌")
    @PreAuthorize("@ss.hasPermission('product:brand:update')")
    public CommonResult<Boolean> updateBrand(@Valid @RequestBody ProductBrandUpdateReqVO updateReqVO) {
        brandService.updateBrand(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除品牌")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:brand:delete')")
    public CommonResult<Boolean> deleteBrand(@RequestParam("id") Long id) {
        brandService.deleteBrand(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得品牌")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<ProductBrandRespVO> getBrand(@RequestParam("id") Long id) {
        ProductBrandDO brand = brandService.getBrand(id);
        return success(ProductBrandConvert.INSTANCE.convert(brand));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取品牌精简信息列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<ProductBrandSimpleRespVO>> getSimpleBrandList() {
        // 获取品牌列表，只要开启状态的
        List<ProductBrandDO> list = brandService.getBrandListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(ProductBrandConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得品牌分页")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<PageResult<ProductBrandRespVO>> getBrandPage(@Valid ProductBrandPageReqVO pageVO) {
        PageResult<ProductBrandDO> pageResult = brandService.getBrandPage(pageVO);
        return success(ProductBrandConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list")
    @Operation(summary = "获得品牌列表")
    @PreAuthorize("@ss.hasPermission('product:brand:query')")
    public CommonResult<List<ProductBrandRespVO>> getBrandList(@Valid ProductBrandListReqVO listVO) {
        List<ProductBrandDO> list = brandService.getBrandList(listVO);
        list.sort(Comparator.comparing(ProductBrandDO::getSort));
        return success(ProductBrandConvert.INSTANCE.convertList(list));
    }

}
