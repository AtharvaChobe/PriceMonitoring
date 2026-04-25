package com.PriceMonitoring.DataService.service;

import com.PriceMonitoring.DataService.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long TTL = 1500; // minutes

    private String buildKey(String city, String metal) {
        return city.toLowerCase() + ":" + metal.toLowerCase();
    }

    public void savePrice(String city, String metal, Price price) {
        String key = buildKey(city, metal);
        redisTemplate.opsForValue().set(key, price, TTL, TimeUnit.MINUTES);
    }

    public Price getPrice(String city, String metal) {
        String key = buildKey(city, metal);
        return (Price) redisTemplate.opsForValue().get(key);
    }
}
