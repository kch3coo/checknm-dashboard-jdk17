package ryd.checknm.dashboard.module.iot.dal.dataobject.product;

import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * IoT 产品分类 DO
 *
 * @author 芋道源码
 */
@TableName("iot_product_category")
@KeySequence("iot_product_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotProductCategoryDO extends BaseDO {

    /**
     * 分类 ID
     */
    @TableId
    private Long id;
    /**
     * 分类名字
     */
    private String name;
    /**
     * 分类排序
     */
    private Integer sort;
    /**
     * 分类状态
     *
     * 枚举 {@link ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;
    /**
     * 分类描述
     */
    private String description;

}