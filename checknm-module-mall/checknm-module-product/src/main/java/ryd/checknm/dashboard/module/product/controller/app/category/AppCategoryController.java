package ryd.checknm.dashboard.module.product.controller.app.category;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.product.controller.app.category.vo.AppCategoryRespVO;
import ryd.checknm.dashboard.module.product.dal.dataobject.category.ProductCategoryDO;
import ryd.checknm.dashboard.module.product.service.category.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 商品分类")
@RestController
@RequestMapping("/product/category")
@Validated
public class AppCategoryController {

    @Resource
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "获得商品分类列表")
    @PermitAll
    public CommonResult<List<AppCategoryRespVO>> getProductCategoryList() {
        List<ProductCategoryDO> list = categoryService.getEnableCategoryList();
        list.sort(Comparator.comparing(ProductCategoryDO::getSort));
        return success(BeanUtils.toBean(list, AppCategoryRespVO.class));
    }

    @GetMapping("/list-by-ids")
    @Operation(summary = "获得商品分类列表，指定编号")
    @Parameter(name = "ids", description = "商品分类编号数组", required = true)
    @PermitAll
    public CommonResult<List<AppCategoryRespVO>> getProductCategoryList(@RequestParam("ids") List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return success(Collections.emptyList());
        }
        List<ProductCategoryDO> list = categoryService.getEnableCategoryList(ids);
        list.sort(Comparator.comparing(ProductCategoryDO::getSort));
        return success(BeanUtils.toBean(list, AppCategoryRespVO.class));
    }

}
