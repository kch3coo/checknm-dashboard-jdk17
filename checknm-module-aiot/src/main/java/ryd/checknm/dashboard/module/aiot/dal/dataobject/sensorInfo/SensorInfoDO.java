package ryd.checknm.dashboard.module.aiot.dal.dataobject.sensorInfo;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;

/**
 * 传感器信息 DO
 *
 * @author 芋道源码
 */
@TableName("aiot_sensor_info")
@KeySequence("aiot_sensor_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorInfoDO extends BaseDO {

    /**
     * 传感器ID
     */
    @TableId
    private Long id;
    /**
     * UUID
     */
    private String uuid;
    /**
     * MAC
     */
    private String mac;
    /**
     * 网关MAC
     */
    private String gatewayMac;
    /**
     * 类型
     *
     * 枚举 {@link TODO aiot_sensor_type 对应的类}
     */
    private Integer type;
    /**
     * 厂商
     */
    private String producer;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 版本信息
     */
    private String version;
    /**
     * 状态
     *
     * 枚举 {@link TODO aiot_sensor_type 对应的类}
     */
    private Integer status;


}