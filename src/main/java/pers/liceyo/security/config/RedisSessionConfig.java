package pers.liceyo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redisSession配置
 * @author liceyo
 * @version 2018/6/27
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60,redisNamespace = "lss-spring-session")
public class RedisSessionConfig {
}
