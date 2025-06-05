package ryd.checknm.dashboard.framework.idempotent.config;

import ryd.checknm.dashboard.framework.idempotent.core.aop.IdempotentAspect;
import ryd.checknm.dashboard.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import ryd.checknm.dashboard.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import ryd.checknm.dashboard.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import ryd.checknm.dashboard.framework.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import ryd.checknm.dashboard.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import ryd.checknm.dashboard.framework.redis.config.ChecknmRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = ChecknmRedisAutoConfiguration.class)
public class ChecknmIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
