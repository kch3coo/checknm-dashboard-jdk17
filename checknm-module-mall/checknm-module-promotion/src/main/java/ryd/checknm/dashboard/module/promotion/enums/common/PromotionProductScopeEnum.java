package ryd.checknm.dashboard.module.promotion.enums.common;

import ryd.checknm.dashboard.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 营销的商品范围枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum PromotionProductScopeEnum implements ArrayValuable<Integer> {

    ALL(1, "全部商品"),
    SPU(2, "指定商品"),
    CATEGORY(3, "指定品类");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(PromotionProductScopeEnum::getScope).toArray(Integer[]::new);

    /**
     * 范围值
     */
    private final Integer scope;
    /**
     * 范围名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public static boolean isAll(Integer scope) {
        return Objects.equals(scope, ALL.scope);
    }

    public static boolean isSpu(Integer scope) {
        return Objects.equals(scope, SPU.scope);
    }

    public static boolean isCategory(Integer scope) {
        return Objects.equals(scope, CATEGORY.scope);
    }

}
