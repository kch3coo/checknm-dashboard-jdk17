package ryd.checknm.dashboard.module.iot.dal.dataobject.thingmodel;

import ryd.checknm.dashboard.framework.mybatis.core.dataobject.BaseDO;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelEvent;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelProperty;
import ryd.checknm.dashboard.module.iot.controller.admin.thingmodel.model.ThingModelService;
import ryd.checknm.dashboard.module.iot.dal.dataobject.product.IotProductDO;
import ryd.checknm.dashboard.module.iot.enums.thingmodel.IotThingModelTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IoT 产品物模型功能 DO
 * <p>
 * 每个 {@link IotProductDO} 和 {@link IotThingModelDO} 是“一对多”的关系，它的每个属性、事件、服务都对应一条记录
 *
 * @author 芋道源码
 */
@TableName(value = "iot_thing_model", autoResultMap = true)
@KeySequence("iot_thing_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotThingModelDO extends BaseDO {

    /**
     * 物模型功能编号
     */
    @TableId
    private Long id;

    /**
     * 功能标识
     */
    private String identifier;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 功能描述
     */
    private String description;

    /**
     * 产品标识
     * <p>
     * 关联 {@link IotProductDO#getId()}
     */
    private Long productId;
    /**
     * 产品标识
     * <p>
     * 关联 {@link IotProductDO#getProductKey()}
     */
    private String productKey;

    /**
     * 功能类型
     * <p>
     * 枚举 {@link IotThingModelTypeEnum}
     */
    private Integer type;

    /**
     * 属性
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThingModelProperty property;

    /**
     * 事件
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThingModelEvent event;

    /**
     * 服务
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ThingModelService service;

}