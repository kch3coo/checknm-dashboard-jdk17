package ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Blob;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MachineInfoRespVO {

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备id")
    private Long id;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("公司名称")
    private String companyName;

    @Schema(description = "工厂名称")
    @ExcelProperty("工厂名称")
    private String factoryName;

    @Schema(description = "设备产线")
    @ExcelProperty("设备产线")
    private String productLine;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备名称")
    private String deviceName;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备类型")
    private String deviceType;

    @Schema(description = "设备图片")
    @ExcelProperty("设备图片")
    private byte[] deviceImage;

    @Schema(description = "最新检测时间")
    @ExcelProperty("最新检测时间")
    private LocalDateTime lastCheckTime;

    @Schema(description = "最新检测人员")
    @ExcelProperty("最新检测人员")
    private String lastMaintainer;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}