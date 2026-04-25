package com.PriceMonitoring.UserService.controller;

import com.PriceMonitoring.UserService.dto.LoginRequest;
import com.PriceMonitoring.UserService.dto.RegisterRequest;
import com.PriceMonitoring.UserService.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    private String registerCntrlr(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    private String loginCntrlr(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }
}
