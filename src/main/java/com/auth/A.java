package com.auth;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class A {
    public static void main(String[] args) {
//        PasswordEncoder encoder  = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("testing"));
        String hashPassword = BCrypt.hashpw("testing", BCrypt.gensalt(10));
        System.out.println(hashPassword);
    }
}
