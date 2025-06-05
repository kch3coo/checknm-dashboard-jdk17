package ryd.checknm.dashboard.module.iot.job.plugin;

import cn.hutool.core.util.StrUtil;
import ryd.checknm.dashboard.framework.quartz.core.handler.JobHandler;
import ryd.checknm.dashboard.framework.tenant.core.job.TenantJob;
import ryd.checknm.dashboard.module.iot.service.plugin.IotPluginInstanceService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * IoT 插件实例离线检查 Job
 *
 * @author 芋道源码
 */
@Component
public class IotPluginInstancesJob implements JobHandler {

    /**
     * 插件离线超时时间
     *
     * TODO 芋艿：暂定 10 分钟，后续看要不要做配置
     */
    public static final Duration OFFLINE_TIMEOUT = Duration.ofMinutes(10);

    @Resource
    private IotPluginInstanceService pluginInstanceService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = pluginInstanceService.offlineTimeoutPluginInstance(
                LocalDateTime.now().minus(OFFLINE_TIMEOUT));
        return StrUtil.format("离线超时插件实例数量为: {}", count);
    }

}