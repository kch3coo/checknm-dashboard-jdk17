package ryd.checknm.dashboard.module.pay.framework.pay.config;

import ryd.checknm.dashboard.module.pay.framework.pay.core.client.PayClientFactory;
import ryd.checknm.dashboard.module.pay.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PayProperties.class)
public class PayConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
