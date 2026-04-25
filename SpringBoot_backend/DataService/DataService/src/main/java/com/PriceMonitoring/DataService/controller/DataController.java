package com.PriceMonitoring.DataService.controller;

import com.PriceMonitoring.DataService.dtos.GoldApiResponseDto;
import com.PriceMonitoring.DataService.dtos.SilverApiResponseDto;
import com.PriceMonitoring.DataService.entity.Price;
import com.PriceMonitoring.DataService.service.RedisService;
import com.PriceMonitoring.DataService.util.FetchApiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private FetchApiData fetchApiData;

    @GetMapping
    public ResponseEntity<Double> getData(@RequestParam String metal, @RequestParam String city){
        Price result = redisService.getPrice(city, metal);
        if(result == null){
            if(metal.equalsIgnoreCase("gold")){
                GoldApiResponseDto res = (GoldApiResponseDto) fetchApiData.getMarketData(metal, city).block();
                Price price = new Price();
                price.setCity(city);
                price.setMetal(metal);
                price.setPrice(res.getPrice24k());
                price.setTimeStamp(LocalDateTime.now());
                redisService.savePrice(city,metal,price);
                return ResponseEntity.ok(price.getPrice());
            } else{
                SilverApiResponseDto res = (SilverApiResponseDto) fetchApiData.getMarketData(metal, city).block();
                Price price = new Price();
                price.setCity(city);
                price.setMetal(metal);
                price.setPrice(res.getPrice());
                price.setTimeStamp(LocalDateTime.now());
                redisService.savePrice(city,metal,price);
                return ResponseEntity.ok(price.getPrice());
            }
        }
        return ResponseEntity.ok(result.getPrice());
    }
}