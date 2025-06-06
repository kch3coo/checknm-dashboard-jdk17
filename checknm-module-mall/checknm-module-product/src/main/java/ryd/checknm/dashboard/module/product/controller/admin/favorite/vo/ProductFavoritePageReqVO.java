package ryd.checknm.dashboard.module.product.controller.admin.favorite.vo;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 商品收藏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductFavoritePageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "5036")
    private Long userId;

}
