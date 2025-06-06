package ryd.checknm.dashboard.module.iot.controller.admin.device.vo.control;

import ryd.checknm.dashboard.framework.common.validation.InEnum;
import ryd.checknm.dashboard.module.iot.enums.device.IotDeviceMessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - IoT 设备上行 Request VO") // 属性上报、事件上报、状态变更等
@Data
public class IotDeviceUpstreamReqVO {

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "177")
    @NotNull(message = "设备编号不能为空")
    private Long id;

    @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "property")
    @NotEmpty(message = "消息类型不能为空")
    @InEnum(IotDeviceMessageTypeEnum.class)
    private String type;

    @Schema(description = "标识符", requiredMode = Schema.RequiredMode.REQUIRED, example = "report")
    @NotEmpty(message = "标识符不能为空")
    private String identifier; // 参见 IotDeviceMessageIdentifierEnum 枚举类

    @Schema(description = "请求参数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Object data; // 例如说：属性上报的 properties、事件上报的 params

}
