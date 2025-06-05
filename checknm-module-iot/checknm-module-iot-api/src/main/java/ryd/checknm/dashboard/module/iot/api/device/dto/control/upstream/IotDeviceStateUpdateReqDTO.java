package ryd.checknm.dashboard.module.iot.api.device.dto.control.upstream;

import ryd.checknm.dashboard.framework.common.validation.InEnum;
import ryd.checknm.dashboard.module.iot.enums.device.IotDeviceStateEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * IoT 设备【状态】更新 Request DTO
 *
 * @author 芋道源码
 */
@Data
public class IotDeviceStateUpdateReqDTO extends IotDeviceUpstreamAbstractReqDTO {

    /**
     * 设备状态
     */
    @NotNull(message = "设备状态不能为空")
    @InEnum(IotDeviceStateEnum.class) // 只使用：在线、离线
    private Integer state;

}
