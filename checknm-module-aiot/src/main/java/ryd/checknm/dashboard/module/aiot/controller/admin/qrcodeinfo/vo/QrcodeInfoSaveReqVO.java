package ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 二维码信息新增/修改 Request VO")
@Data
public class QrcodeInfoSaveReqVO {

    @Schema(description = "二维码id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "二维码信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "二维码信息不能为空")
    private String info;

    @Schema(description = "二维码类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "二维码类型不能为空")
    private String type;

    @Schema(description = "二维码状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "二维码状态不能为空")
    private Integer status;

}