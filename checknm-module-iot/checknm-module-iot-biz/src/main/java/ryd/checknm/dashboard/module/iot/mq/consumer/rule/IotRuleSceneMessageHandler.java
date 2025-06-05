package ryd.checknm.dashboard.module.iot.mq.consumer.rule;

import ryd.checknm.dashboard.module.iot.mq.message.IotDeviceMessage;
import ryd.checknm.dashboard.module.iot.service.rule.IotRuleSceneService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 针对 {@link IotDeviceMessage} 的消费者，处理规则场景
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class IotRuleSceneMessageHandler {

    @Resource
    private IotRuleSceneService ruleSceneService;

    @EventListener
    @Async
    public void onMessage(IotDeviceMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        ruleSceneService.executeRuleSceneByDevice(message);
    }

}
