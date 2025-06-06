package ryd.checknm.dashboard.module.iot.mq.producer.device;

import ryd.checknm.dashboard.module.iot.mq.message.IotDeviceMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * IoT 设备相关消息的 Producer
 *
 * @author alwayssuper
 * @since 2024/12/17 16:35
 */
@Slf4j
@Component
public class IotDeviceProducer {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 发送 {@link IotDeviceMessage} 消息
     *
     * @param thingModelMessage 物模型消息
     */
    public void sendDeviceMessage(IotDeviceMessage thingModelMessage) {
        applicationContext.publishEvent(thingModelMessage);
    }

}
