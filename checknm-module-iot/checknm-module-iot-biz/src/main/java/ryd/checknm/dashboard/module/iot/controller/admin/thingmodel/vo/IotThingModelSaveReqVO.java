package ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.vo;

import ryd.checknm.dashboard.framework.common.validation.InEnum;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelEvent;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelProperty;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelService;
import ryd.checknm.dashboard.module.iot.enums.thingmodel.IotThingModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - IoT 产品物模型新增/修改 Request VO")
@Data
public class IotThingModelSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "产品ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @Schema(description = "产品标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "temperature_001")
    @NotEmpty(message = "产品标识不能为空")
    private String productKey;

    @Schema(description = "功能标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "temp_monitor")
    @NotEmpty(message = "功能标识不能为空")
    private String identifier;

    @Schema(description = "功能名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "温度监测器")
    @NotEmpty(message = "功能名称不能为空")
    private String name;

    @Schema(description = "功能描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "用于监测环境温度的传感器")
    private String description;

    @Schema(description = "功能类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "功能类型不能为空")
    @InEnum(IotThingModelTypeEnum.class)
    private Integer type;

    @Schema(description = "属性", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private ThingModelProperty property;

    @Schema(description = "服务", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private ThingModelService service;

    @Schema(description = "事件", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private ThingModelEvent event;

}