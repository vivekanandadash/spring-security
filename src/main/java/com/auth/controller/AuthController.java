package com.auth.controller;

import com.auth.dto.APIResponse;
import com.auth.dto.LoginDto;
import com.auth.dto.UserDto;
import com.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private AuthService authService;
    private AuthenticationManager authManager;

    public AuthController(AuthService authService, AuthenticationManager authManager) {
        this.authService = authService;
        this.authManager = authManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> createUser(
            @RequestBody UserDto userDto
    ) {
        APIResponse<String> response = authService.register(userDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(
            @RequestBody LoginDto loginDto
    ) {
        APIResponse<String> response = new APIResponse<>();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);
            System.out.println(authenticate.isAuthenticated());
            response.setMessage("Login Successfully");
            response.setStatus(200);
            response.setData("Transaction Completed");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            System.out.println("login failed :" + e.getMessage());
            response.setMessage("Check Your Credential");
            response.setStatus(404);
            response.setData("User Not Found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }


    }

}
