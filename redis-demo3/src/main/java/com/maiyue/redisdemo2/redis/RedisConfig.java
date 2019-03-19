package com.maiyue.redisdemo2.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Component
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis_ip}")
    private String ip;
    @Value("${redis_port}")
    private int port;
    @Value("${redis_timeout}")
    private int timeout;
    @Value("${redis_password}")
    private String password;
    @Value("${redis_max_active}")
    private int maxActive;
    @Value("${redis_max_idle}")
    private int maxIdle;
    @Value("${redis_max_wait}")
    private long maxWait;
    @Value("${redis_text_on_borrow}")
    private boolean testOnBorrow;
    @Value("${redis_text_on_return}")
    private boolean testOnReturn;


    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + ip + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(this.maxActive);
        jedisPoolConfig.setMaxIdle(this.maxIdle);
        jedisPoolConfig.setMaxWaitMillis(this.maxWait);
        jedisPoolConfig.setTestOnBorrow(this.testOnBorrow);
        jedisPoolConfig.setTestOnReturn(this.testOnReturn);
        JedisPool jedisPool = (StringUtils.isNotBlank(this.password) ? new JedisPool(jedisPoolConfig, this.ip, this.port, this.timeout, this.password) : new JedisPool(jedisPoolConfig, this.ip, this.port, this.timeout));
        return jedisPool;
    }
}
