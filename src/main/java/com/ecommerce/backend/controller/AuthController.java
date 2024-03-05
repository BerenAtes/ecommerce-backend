package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.RegisterUser;
import com.ecommerce.backend.dto.UserResponse;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User register(@Validated @RequestBody RegisterUser registerUser){
        return authenticationService.register(registerUser.fullName(), registerUser.email(),
                registerUser.password(),registerUser.roleId());
    }


}
