package ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo;

import lombok.*;

import java.sql.Blob;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备信息分页 Request VO")
@Data
public class MachineInfoPageReqVO extends PageParam {

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "工厂名称")
    private String factoryName;

    @Schema(description = "设备产线")
    private String productLine;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "设备类型")
    private String deviceType;

    @Schema(description = "设备图片")
    private Blob deviceImage;

    @Schema(description = "最新检测时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] lastCheckTime;

    @Schema(description = "最新检测人员")
    private String lastMaintainer;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}