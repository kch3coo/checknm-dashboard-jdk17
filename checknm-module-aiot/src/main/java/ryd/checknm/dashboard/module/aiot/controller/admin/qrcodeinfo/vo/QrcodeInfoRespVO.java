package ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import ryd.checknm.dashboard.framework.excel.core.annotations.DictFormat;
import ryd.checknm.dashboard.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 二维码信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QrcodeInfoRespVO {

    @Schema(description = "二维码id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("二维码id")
    private Integer id;

    @Schema(description = "二维码信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("二维码信息")
    private String info;

    @Schema(description = "二维码创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("二维码创建时间")
    private LocalDateTime createTime;

    @Schema(description = "二维码类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "二维码类型", converter = DictConvert.class)
    @DictFormat("aiot_qrcode_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String type;

    @Schema(description = "二维码状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "二维码状态", converter = DictConvert.class)
    @DictFormat("aiot_qrcode_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

}