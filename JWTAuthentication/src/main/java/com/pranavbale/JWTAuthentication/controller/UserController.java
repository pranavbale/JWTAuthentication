package com.pranavbale.JWTAuthentication.controller;


import com.pranavbale.JWTAuthentication.entity.User;
import com.pranavbale.JWTAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    // gating ng all user
    @GetMapping("/getAll")
    private List<User> getAllUsers() {
        return userService.getUser();
    }


    // get current User
    @GetMapping("/currentUser")
    public String getCurrentUser(Principal principal) {
        return principal.getName();
    }
}
