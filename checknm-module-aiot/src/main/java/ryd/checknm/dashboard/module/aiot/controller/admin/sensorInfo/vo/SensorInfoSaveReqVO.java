package ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 传感器信息新增/修改 Request VO")
@Data
public class SensorInfoSaveReqVO {

    @Schema(description = "传感器ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "UUID", requiredMode = Schema.RequiredMode.REQUIRED, example = "98E9")
    @NotEmpty(message = "UUID不能为空")
    private String uuid;

    @Schema(description = "MAC", requiredMode = Schema.RequiredMode.REQUIRED, example = "00:80:E1:26:98:E9")
    @NotEmpty(message = "MAC不能为空")
    private String mac;

    @Schema(description = "网关MAC", example = "CC:1B:E0:E4:72:04")
    private String gatewayMac;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "类型不能为空")
    private Integer type;

    @Schema(description = "厂商")
    private String producer;

    @Schema(description = "二维码", requiredMode = Schema.RequiredMode.REQUIRED, example = "KS24A-98E9")
    @NotEmpty(message = "二维码不能为空")
    private String qrCode;

    @Schema(description = "版本信息")
    private String version;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer status;

}