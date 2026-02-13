package com.auth.service;

import com.auth.dto.APIResponse;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public APIResponse<String> register(UserDto dto){
        if (userRepository.existsByUsername(dto.getUsername())){
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }
        if(userRepository.existsByEmail(dto.getEmail())) {
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);

        return null;
    }
}
