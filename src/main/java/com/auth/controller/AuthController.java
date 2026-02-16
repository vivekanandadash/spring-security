package com.auth.controller;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;
import com.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> createUser(
            @RequestBody UserDto userDto
            ){
        APIResponse<String> response = authService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

    }
}
