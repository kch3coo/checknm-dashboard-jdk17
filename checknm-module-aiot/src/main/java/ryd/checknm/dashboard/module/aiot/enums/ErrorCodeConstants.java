package ryd.checknm.dashboard.module.aiot.enums;

import ryd.checknm.dashboard.framework.common.exception.ErrorCode;

/**
 * AIOT 错误码枚举类
 *
 * AIOT 系统，使用 1-060-000-000 段
 */
public interface ErrorCodeConstants {
    ErrorCode SENSOR_INFO_NOT_EXISTS = new ErrorCode(1-060-300-005, "传感器信息不存在");
    ErrorCode SENSOR_INFO_ALREADY_EXISTS = new ErrorCode(1-060-300-016, "传感器信息已存在");
    ErrorCode MACHINE_INFO_NOT_EXISTS = new ErrorCode(1-060-300-011, "设备信息不存在");
    ErrorCode MACHINE_LOCATION_INFO_NOT_EXISTS = new ErrorCode(1-060-300-007, "设备位置信息不存在");
    ErrorCode QRCODE_INFO_NOT_EXISTS = new ErrorCode(1-060-301-001, "二维码信息不存在");

}
