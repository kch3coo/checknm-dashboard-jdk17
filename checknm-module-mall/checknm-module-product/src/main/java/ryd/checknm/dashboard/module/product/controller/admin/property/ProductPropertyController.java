package ryd.checknm.dashboard.module.product.controller.admin.property;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.product.controller.admin.property.vo.property.ProductPropertyPageReqVO;
import ryd.checknm.dashboard.module.product.controller.admin.property.vo.property.ProductPropertyRespVO;
import ryd.checknm.dashboard.module.product.controller.admin.property.vo.property.ProductPropertySaveReqVO;
import ryd.checknm.dashboard.module.product.dal.dataobject.property.ProductPropertyDO;
import ryd.checknm.dashboard.module.product.service.property.ProductPropertyService;
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

@Tag(name = "管理后台 - 商品属性项")
@RestController
@RequestMapping("/product/property")
@Validated
public class ProductPropertyController {

    @Resource
    private ProductPropertyService productPropertyService;

    @PostMapping("/create")
    @Operation(summary = "创建属性项")
    @PreAuthorize("@ss.hasPermission('product:property:create')")
    public CommonResult<Long> createProperty(@Valid @RequestBody ProductPropertySaveReqVO createReqVO) {
        return success(productPropertyService.createProperty(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新属性项")
    @PreAuthorize("@ss.hasPermission('product:property:update')")
    public CommonResult<Boolean> updateProperty(@Valid @RequestBody ProductPropertySaveReqVO updateReqVO) {
        productPropertyService.updateProperty(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除属性项")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('product:property:delete')")
    public CommonResult<Boolean> deleteProperty(@RequestParam("id") Long id) {
        productPropertyService.deleteProperty(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得属性项")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:property:query')")
    public CommonResult<ProductPropertyRespVO> getProperty(@RequestParam("id") Long id) {
        ProductPropertyDO property = productPropertyService.getProperty(id);
        return success(BeanUtils.toBean(property, ProductPropertyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得属性项分页")
    @PreAuthorize("@ss.hasPermission('product:property:query')")
    public CommonResult<PageResult<ProductPropertyRespVO>> getPropertyPage(@Valid ProductPropertyPageReqVO pageVO) {
        PageResult<ProductPropertyDO> pageResult = productPropertyService.getPropertyPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ProductPropertyRespVO.class));
    }

    @GetMapping("/simple-list")
    @Operation(summary = "获得属性项精简列表")
    public CommonResult<List<ProductPropertyRespVO>> getPropertySimpleList() {
        List<ProductPropertyDO> list = productPropertyService.getPropertyList();
        return success(convertList(list, property -> new ProductPropertyRespVO() // 只返回 id、name 属性
                .setId(property.getId()).setName(property.getName())));
    }

}
