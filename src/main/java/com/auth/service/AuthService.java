package com.auth.service;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public APIResponse<String> register(UserDto dto){
        APIResponse<String> response = new APIResponse<>();
        if (userRepository.existsByUsername(dto.getUsername())){
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }
        if(userRepository.existsByEmail(dto.getEmail())) {
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        response.setMessage("Registration Successful");
        response.setStatus(201);
        response.setData("Transaction Completed");
        return response;
    }
}
