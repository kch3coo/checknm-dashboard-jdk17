package ryd.checknm.dashboard.module.bpm.enums.definition;

import ryd.checknm.dashboard.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户任务超时处理类型枚举
 *
 * @author jason
 */
@Getter
@AllArgsConstructor
public enum BpmUserTaskTimeoutHandlerTypeEnum implements ArrayValuable<Integer> {

    REMINDER(1,"自动提醒"),
    APPROVE(2, "自动同意"),
    REJECT(3, "自动拒绝");

    private final Integer type;
    private final String name;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(BpmUserTaskTimeoutHandlerTypeEnum::getType).toArray(Integer[]::new);

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
