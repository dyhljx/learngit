package com.maiyue.redisdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableDiscoveryClient
@PropertySources({
    @PropertySource(value = "classpath:redis.properties", ignoreResourceNotFound = false)
})
public class RedisDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemo2Application.class, args);
    }
}
