package com.PriceMonitoring.AlertService.service;

import com.PriceMonitoring.AlertService.dtos.UserDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service" , url = "${USER_SERVICE_URL}")
public interface UserDataClient {
    @GetMapping("/userData/{id}")
    UserDataDto getUserData(@PathVariable long id);
}