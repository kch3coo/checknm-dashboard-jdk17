package ryd.checknm.dashboard.module.product.api.category;

import ryd.checknm.dashboard.module.product.service.category.ProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;

/**
 * 商品分类 API 接口实现类
 *
 * @author owen
 */
@Service
@Validated
public class ProductCategoryApiImpl implements ProductCategoryApi {

    @Resource
    private ProductCategoryService productCategoryService;

    @Override
    public void validateCategoryList(Collection<Long> ids) {
        productCategoryService.validateCategoryList(ids);
    }

}
