package ryd.checknm.dashboard.module.aiot.dal.dataobject.machineInfo;

import lombok.*;

import java.sql.Blob;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备信息 DO
 *
 * @author kch3coo
 */
@TableName("aiot_machine_info")
@KeySequence("aiot_machine_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineInfoDO extends BaseDO {

    /**
     * 设备id
     */
    @TableId
    private Long id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 工厂名称
     */
    private String factoryName;
    /**
     * 设备产线
     */
    private String productLine;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备图片
     */
    private Blob deviceImage;
    /**
     * 最新检测时间
     */
    private LocalDateTime lastCheckTime;
    /**
     * 最新检测人员
     */
    private String lastMaintainer;


}