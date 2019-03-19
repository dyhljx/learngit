package com.maiyue.redisdemo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maiyue.redisdemo2.redis.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

                @Autowired
                private RedisService redisService;

                @RequestMapping("/setValue")
                public String setValue(){
                	String key="ceshi";
                	String str=redisService.set(key, "你好哈哈哈");
                	return str;
                	
                  }


}
