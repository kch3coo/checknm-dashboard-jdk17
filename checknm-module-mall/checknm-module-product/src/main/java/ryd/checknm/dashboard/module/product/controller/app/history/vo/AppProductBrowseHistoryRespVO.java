package ryd.checknm.dashboard.module.product.controller.app.history.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 商品浏览记录 Response VO")
@Data
public class AppProductBrowseHistoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "商品 SPU 编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29502")
    private Long spuId;

    // ========== 商品相关字段 ==========

    @Schema(description = "商品 SPU 名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String spuName;

    @Schema(description = "商品封面图", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/pic.png")
    private String picUrl;

    @Schema(description = "商品单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer price;

    @Schema(description = "商品销量", requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
    private Integer salesCount;

    @Schema(description = "库存", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    private Integer stock;

}
