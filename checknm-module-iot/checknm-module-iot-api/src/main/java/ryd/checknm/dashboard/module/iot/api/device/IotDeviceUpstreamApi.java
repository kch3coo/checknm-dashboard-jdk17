package ryd.checknm.dashboard.module.iot.api.device;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.module.iot.api.device.dto.control.upstream.*;
import ryd.checknm.dashboard.module.iot.enums.ApiConstants;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 设备数据 Upstream 上行 API
 *
 * 目的：设备 -> 插件 -> 服务端
 *
 * @author haohao
 */
public interface IotDeviceUpstreamApi {

    String PREFIX = ApiConstants.PREFIX + "/device/upstream";

    // ========== 设备相关 ==========

    /**
     * 更新设备状态
     *
     * @param updateReqDTO 更新设备状态 DTO
     */
    @PostMapping(PREFIX + "/update-state")
    CommonResult<Boolean> updateDeviceState(@Valid @RequestBody IotDeviceStateUpdateReqDTO updateReqDTO);

    /**
     * 上报设备属性数据
     *
     * @param reportReqDTO 上报设备属性数据 DTO
     */
    @PostMapping(PREFIX + "/report-property")
    CommonResult<Boolean> reportDeviceProperty(@Valid @RequestBody IotDevicePropertyReportReqDTO reportReqDTO);

    /**
     * 上报设备事件数据
     *
     * @param reportReqDTO 设备事件
     */
    @PostMapping(PREFIX + "/report-event")
    CommonResult<Boolean> reportDeviceEvent(@Valid @RequestBody IotDeviceEventReportReqDTO reportReqDTO);

    // TODO @芋艿：这个需要 plugins 接入下
    /**
     * 注册设备
     *
     * @param registerReqDTO 注册设备 DTO
     */
    @PostMapping(PREFIX + "/register")
    CommonResult<Boolean> registerDevice(@Valid @RequestBody IotDeviceRegisterReqDTO registerReqDTO);

    // TODO @芋艿：这个需要 plugins 接入下
    /**
     * 注册子设备
     *
     * @param registerReqDTO 注册子设备 DTO
     */
    @PostMapping(PREFIX + "/register-sub")
    CommonResult<Boolean> registerSubDevice(@Valid @RequestBody IotDeviceRegisterSubReqDTO registerReqDTO);

    // TODO @芋艿：这个需要 plugins 接入下
    /**
     * 注册设备拓扑
     *
     * @param addReqDTO 注册设备拓扑 DTO
     */
    @PostMapping(PREFIX + "/add-topology")
    CommonResult<Boolean> addDeviceTopology(@Valid @RequestBody IotDeviceTopologyAddReqDTO addReqDTO);

    // TODO @芋艿：考虑 http 认证
    /**
     * 认证 Emqx 连接
     *
     * @param authReqDTO 认证 Emqx 连接 DTO
     */
    @PostMapping(PREFIX + "/authenticate-emqx-connection")
    CommonResult<Boolean> authenticateEmqxConnection(@Valid @RequestBody IotDeviceEmqxAuthReqDTO authReqDTO);

    // ========== 插件相关 ==========

    /**
     * 心跳插件实例
     *
     * @param heartbeatReqDTO 心跳插件实例 DTO
     */
    @PostMapping(PREFIX + "/heartbeat-plugin-instance")
    CommonResult<Boolean> heartbeatPluginInstance(@Valid @RequestBody IotPluginInstanceHeartbeatReqDTO heartbeatReqDTO);

}