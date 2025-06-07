package ryd.checknm.dashboard.module.aiot.dal.dataobject.qrcodeinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;

/**
 * 二维码信息 DO
 *
 * @author RYD
 */
@TableName("aiot_qrcode_info")
@KeySequence("aiot_qrcode_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrcodeInfoDO extends BaseDO {

    /**
     * 二维码id
     */
    @TableId
    private Integer id;
    /**
     * 二维码信息
     */
    private String info;
    /**
     * 二维码类型
     *
     * 枚举 {@link TODO aiot_qrcode_type 对应的类}
     */
    private String type;
    /**
     * 二维码状态
     *
     * 枚举 {@link TODO aiot_qrcode_status 对应的类}
     */
    private Integer status;


}