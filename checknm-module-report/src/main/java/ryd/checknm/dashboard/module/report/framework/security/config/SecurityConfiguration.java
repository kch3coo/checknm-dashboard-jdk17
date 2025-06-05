package ryd.checknm.dashboard.module.report.framework.security.config;

import ryd.checknm.dashboard.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * Report 模块的 Security 配置
 */
@Configuration("reportSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("reportAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
                // 积木报表
                registry.requestMatchers("/jmreport/**").permitAll();
                // 积木仪表盘
                registry.requestMatchers("/drag/**").permitAll();
                registry.requestMatchers("/jimubi/**").permitAll();
            }

        };
    }

}
