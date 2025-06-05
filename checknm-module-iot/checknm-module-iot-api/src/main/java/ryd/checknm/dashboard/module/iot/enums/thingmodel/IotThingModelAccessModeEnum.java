package ryd.checknm.dashboard.module.iot.enums.thingmodel;

import ryd.checknm.dashboard.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * IoT 产品物模型属性读取类型枚举
 *
 * @author ahh
 */
@AllArgsConstructor
@Getter
public enum IotThingModelAccessModeEnum implements ArrayValuable<String> {

    READ_ONLY("r"),
    READ_WRITE("rw");

    public static final String[] ARRAYS = Arrays.stream(values()).map(IotThingModelAccessModeEnum::getMode).toArray(String[]::new);

    private final String mode;

    @Override
    public String[] array() {
        return ARRAYS;
    }

}
