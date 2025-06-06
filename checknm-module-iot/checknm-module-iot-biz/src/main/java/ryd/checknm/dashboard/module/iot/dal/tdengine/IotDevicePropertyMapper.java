package ryd.checknm.dashboard.module.iot.dal.tdengine;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import ryd.checknm.dashboard.module.iot.controller.admin.device.vo.data.IotDevicePropertyHistoryPageReqVO;
import ryd.checknm.dashboard.module.iot.controller.admin.device.vo.data.IotDevicePropertyRespVO;
import ryd.checknm.dashboard.module.iot.dal.dataobject.device.IotDeviceDO;
import ryd.checknm.dashboard.module.iot.framework.tdengine.core.TDengineTableField;
import ryd.checknm.dashboard.module.iot.framework.tdengine.core.annotation.TDengineDS;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
@TDengineDS
@InterceptorIgnore(tenantLine = "true") // 避免 SQL 解析，因为 JSqlParser 对 TDengine 的 SQL 解析会报错
public interface IotDevicePropertyMapper {

    List<TDengineTableField> getProductPropertySTableFieldList(@Param("productKey") String productKey);

    void createProductPropertySTable(@Param("productKey") String productKey,
                                     @Param("fields") List<TDengineTableField> fields);

    @SuppressWarnings("SimplifyStreamApiCallChains") // 保持 JDK8 兼容性
    default void alterProductPropertySTable(String productKey,
                                            List<TDengineTableField> oldFields,
                                            List<TDengineTableField> newFields) {
        oldFields.removeIf(field -> StrUtil.equalsAny(field.getField(),
                TDengineTableField.FIELD_TS, "report_time", "device_key"));
        List<TDengineTableField> addFields = newFields.stream().filter( // 新增的字段
                        newField -> oldFields.stream().noneMatch(oldField -> oldField.getField().equals(newField.getField())))
                .collect(Collectors.toList());
        List<TDengineTableField> dropFields = oldFields.stream().filter( // 删除的字段
                        oldField -> newFields.stream().noneMatch(n -> n.getField().equals(oldField.getField())))
                .collect(Collectors.toList());
        List<TDengineTableField> modifyTypeFields = new ArrayList<>(); // 变更类型的字段
        List<TDengineTableField> modifyLengthFields = new ArrayList<>(); // 变更长度的字段
        newFields.forEach(newField -> {
            TDengineTableField oldField = CollUtil.findOne(oldFields, field -> field.getField().equals(newField.getField()));
            if (oldField == null) {
                return;
            }
            if (ObjectUtil.notEqual(oldField.getType(), newField.getType())) {
                modifyTypeFields.add(newField);
                return;
            }
            if (newField.getLength() != null) {
                if (newField.getLength() > oldField.getLength()) {
                    modifyLengthFields.add(newField);
                } else if (newField.getLength() < oldField.getLength()) {
                    // 特殊：TDengine 长度修改时，只允许变长，所以此时认为是修改类型
                    modifyTypeFields.add(newField);
                }
            }
        });

        // 执行
        addFields.forEach(field -> alterProductPropertySTableAddField(productKey, field));
        dropFields.forEach(field -> alterProductPropertySTableDropField(productKey, field));
        modifyLengthFields.forEach(field -> alterProductPropertySTableModifyField(productKey, field));
        modifyTypeFields.forEach(field -> {
            alterProductPropertySTableDropField(productKey, field);
            alterProductPropertySTableAddField(productKey, field);
        });
    }

    void alterProductPropertySTableAddField(@Param("productKey") String productKey,
                                            @Param("field") TDengineTableField field);

    void alterProductPropertySTableModifyField(@Param("productKey") String productKey,
                                               @Param("field") TDengineTableField field);

    void alterProductPropertySTableDropField(@Param("productKey") String productKey,
                                             @Param("field") TDengineTableField field);

    void insert(@Param("device") IotDeviceDO device,
                @Param("properties") Map<String, Object> properties,
                @Param("reportTime") Long reportTime);

    IPage<IotDevicePropertyRespVO> selectPageByHistory(IPage<?> page,
                                                       @Param("reqVO") IotDevicePropertyHistoryPageReqVO reqVO);

}
