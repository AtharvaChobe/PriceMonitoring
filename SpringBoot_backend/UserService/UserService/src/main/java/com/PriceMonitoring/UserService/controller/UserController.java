package com.PriceMonitoring.UserService.controller;

import com.PriceMonitoring.UserService.dto.UserDataDto;
import com.PriceMonitoring.UserService.entity.User;
import com.PriceMonitoring.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userData")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public UserDataDto findUserById(@PathVariable long id){
        User user = userRepository.findById(id).orElseThrow();
        UserDataDto data = new UserDataDto(user.getEmail(), user.getUsername());
        return data;
    }
}
