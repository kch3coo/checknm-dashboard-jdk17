package ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;
import ryd.checknm.dashboard.framework.excel.core.annotations.DictFormat;
import ryd.checknm.dashboard.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 传感器信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SensorInfoRespVO {

    @Schema(description = "传感器ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("传感器ID")
    private Long id;

    @Schema(description = "UUID", requiredMode = Schema.RequiredMode.REQUIRED, example = "98E9")
    @ExcelProperty("UUID")
    private String uuid;

    @Schema(description = "MAC", requiredMode = Schema.RequiredMode.REQUIRED, example = "00:80:E1:26:98:E9")
    @ExcelProperty("MAC")
    private String mac;

    @Schema(description = "网关MAC", example = "CC:1B:E0:E4:72:04")
    @ExcelProperty("网关MAC")
    private String gatewayMac;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "类型", converter = DictConvert.class)
    @DictFormat("aiot_sensor_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer type;

    @Schema(description = "厂商")
    @ExcelProperty("厂商")
    private String producer;

    @Schema(description = "二维码", requiredMode = Schema.RequiredMode.REQUIRED, example = "KS24A-98E9")
    @ExcelProperty("二维码")
    private String qrCode;

    @Schema(description = "版本信息")
    @ExcelProperty("版本信息")
    private String version;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("aiot_sensor_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

}