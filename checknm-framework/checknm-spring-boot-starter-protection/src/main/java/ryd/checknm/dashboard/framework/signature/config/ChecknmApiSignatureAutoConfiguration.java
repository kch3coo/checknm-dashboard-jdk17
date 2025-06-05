package ryd.checknm.dashboard.framework.signature.config;

import ryd.checknm.dashboard.framework.redis.config.ChecknmRedisAutoConfiguration;
import ryd.checknm.dashboard.framework.signature.core.aop.ApiSignatureAspect;
import ryd.checknm.dashboard.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author Zhougang
 */
@AutoConfiguration(after = ChecknmRedisAutoConfiguration.class)
public class ChecknmApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

}
