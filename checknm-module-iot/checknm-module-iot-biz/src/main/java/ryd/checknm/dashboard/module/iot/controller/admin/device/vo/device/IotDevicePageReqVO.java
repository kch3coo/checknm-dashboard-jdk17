package ryd.checknm.dashboard.module.iot.controller.admin.device.vo.device;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.validation.InEnum;
import ryd.checknm.dashboard.module.iot.enums.device.IotDeviceStateEnum;
import ryd.checknm.dashboard.module.iot.enums.product.IotProductDeviceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - IoT 设备分页 Request VO")
@Data
public class IotDevicePageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "王五")
    private String deviceName;

    @Schema(description = "备注名称", example = "张三")
    private String nickname;

    @Schema(description = "产品编号", example = "26202")
    private Long productId;

    @Schema(description = "设备类型", example = "1")
    @InEnum(IotProductDeviceTypeEnum.class)
    private Integer deviceType;

    @Schema(description = "设备状态", example = "1")
    @InEnum(IotDeviceStateEnum.class)
    private Integer status;

    @Schema(description = "设备分组编号", example = "1024")
    private Long groupId;

}