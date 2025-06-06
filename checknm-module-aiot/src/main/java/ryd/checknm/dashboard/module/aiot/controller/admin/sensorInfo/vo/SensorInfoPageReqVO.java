package ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 传感器信息分页 Request VO")
@Data
public class SensorInfoPageReqVO extends PageParam {

    @Schema(description = "UUID", example = "98E9")
    private String uuid;

    @Schema(description = "MAC", example = "00:80:E1:26:98:E9")
    private String mac;

    @Schema(description = "网关MAC", example = "CC:1B:E0:E4:72:04")
    private String gatewayMac;

    @Schema(description = "类型")
    private Integer type;

    @Schema(description = "厂商")
    private String producer;

    @Schema(description = "二维码", example = "KS24A-98E9")
    private String qrCode;

    @Schema(description = "版本信息")
    private String version;

    @Schema(description = "状态")
    private Integer status;

}