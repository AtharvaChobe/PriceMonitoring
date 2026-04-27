package com.PriceMonitoring.AlertService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "data-service", url = "${DATA_SERVICE_URL}")
public interface MarketDataClient {
    @GetMapping("/data")
    double getMarketData(@RequestParam String metal, @RequestParam String city);
}