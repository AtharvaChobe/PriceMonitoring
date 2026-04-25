package com.PriceMonitoring.DataService.service;

import com.PriceMonitoring.DataService.dtos.GoldApiResponseDto;
import com.PriceMonitoring.DataService.dtos.SilverApiResponseDto;
import com.PriceMonitoring.DataService.entity.Price;
import com.PriceMonitoring.DataService.util.FetchApiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarketDataService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private FetchApiData fetchApiData;

    private List<String> cities = List.of("Mumbai", "Delhi", "Chennai","Hyderabad","Banglore");
    private List<String> metals = List.of("gold", "silver");

    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 0 0 * * *")
    public void fetchAndStorePrices() {

        for (String city : cities) {
            for (String metal : metals) {

                double fetchedPrice = callExternalApi(city, metal);

                Price price = new Price();
                price.setCity(city);
                price.setMetal(metal);
                price.setPrice(fetchedPrice);
                price.setTimeStamp(LocalDateTime.now());

                redisService.savePrice(city, metal, price);
            }
        }
    }

    private double callExternalApi(String city, String metal) {
        if(metal.equals("gold")){
            GoldApiResponseDto res = (GoldApiResponseDto) fetchApiData.getMarketData(metal, city).block();
            return res.getPrice24k();
        } else{
            SilverApiResponseDto res = (SilverApiResponseDto) fetchApiData.getMarketData(metal, city).block();
            return res.getPrice();
        }
    }
}
