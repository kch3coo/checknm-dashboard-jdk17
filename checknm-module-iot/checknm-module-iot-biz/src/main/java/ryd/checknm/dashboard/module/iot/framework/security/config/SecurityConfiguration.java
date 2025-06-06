package ryd.checknm.dashboard.module.iot.framework.security.config;

import ryd.checknm.dashboard.framework.security.config.AuthorizeRequestsCustomizer;
import ryd.checknm.dashboard.module.iot.enums.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * IoT 模块的 Security 配置
 */
@Configuration(proxyBeanMethods = false, value = "iotSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("iotAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                // RPC 服务的安全配置
                registry.requestMatchers(ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
