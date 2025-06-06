package ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Blob;
import java.util.*;
import jakarta.validation.constraints.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo.MachineLocationInfoDO;

@Schema(description = "管理后台 - 设备信息新增/修改 Request VO")
@Data
public class MachineInfoSaveReqVO {

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "公司名称不能为空")
    private String companyName;

    @Schema(description = "工厂名称")
    private String factoryName;

    @Schema(description = "设备产线")
    private String productLine;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备名称不能为空")
    private String deviceName;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备类型不能为空")
    private String deviceType;

    @Schema(description = "设备图片")
    private Blob deviceImage;

    @Schema(description = "设备位置信息列表")
    private List<MachineLocationInfoDO> machineLocationInfos;

}