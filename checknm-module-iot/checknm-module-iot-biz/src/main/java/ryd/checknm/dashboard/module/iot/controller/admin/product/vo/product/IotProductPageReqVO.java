package ryd.checknm.dashboard.module.iot.controller.admin.product.vo.product;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - IoT 产品分页 Request VO")
@Data
public class IotProductPageReqVO extends PageParam {

    @Schema(description = "产品名称", example = "李四")
    private String name;

    @Schema(description = "产品标识")
    private String productKey;

}