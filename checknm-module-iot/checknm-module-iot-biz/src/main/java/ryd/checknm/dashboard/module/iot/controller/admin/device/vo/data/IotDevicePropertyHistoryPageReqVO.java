package ryd.checknm.dashboard.module.iot.controller.admin.device.vo.data;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - IoT 设备属性历史分页 Request VO")
@Data
public class IotDevicePropertyHistoryPageReqVO extends PageParam {

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "177")
    @NotNull(message = "设备编号不能为空")
    private Long deviceId;

    @Schema(description = "设备 Key", hidden = true)
    private String deviceKey; // 非前端传递，后端自己查询设置

    @Schema(description = "属性标识符", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "属性标识符不能为空")
    private String identifier;

    @Schema(description = "时间范围", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Size(min = 2, max = 2, message = "请选择时间范围")
    private LocalDateTime[] times;

}