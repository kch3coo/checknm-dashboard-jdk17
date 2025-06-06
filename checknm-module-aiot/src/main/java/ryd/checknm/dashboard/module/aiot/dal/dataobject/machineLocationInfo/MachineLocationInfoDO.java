package ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo;

import lombok.*;

import java.sql.Blob;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备位置信息 DO
 *
 * @author kch3coo
 */
@TableName("aiot_machine_location_info")
@KeySequence("aiot_machine_location_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineLocationInfoDO extends BaseDO {

    /**
     * 设备位置id
     */
    @TableId
    private Long id;
    /**
     * 设备位置二维码
     */
    private String qrCode;
    /**
     * 设备id
     */
    private Long machineId;
    /**
     * 传感器id
     */
    private Long sensorId;
    /**
     * 设备位置
     */
    private String locationInfo;
    /**
     * 最新检测结果
     */
    private Integer latestResult;
    /**
     * 最新检测时间
     */
    private LocalDateTime lastCheckTime;
    /**
     * 最新检测人员
     */
    private String lastMaintainer;
    /**
     * 设备位置图片
     */
    private Blob locationImage;

}