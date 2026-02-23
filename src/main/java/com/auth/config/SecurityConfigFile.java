package com.auth.config;

import com.auth.service.CustomerUserDetailsService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigFile {
//    @PostConstruct
//    public void test(){
//        System.out.println(100);
//    }
    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http){
//        http.authorizeHttpRequests(
//                req ->{
//                    req.anyRequest().permitAll();
//                    System.out.println(100);
//                }
//        );
//        return http.build();
//    }
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
                // 1️⃣ Disable CSRF
        http.csrf(csrf -> csrf.disable())

                // 2️⃣ Authorization rules
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/v1/auth/patient_signup","/api/v1/auth/doctor_signup","/api/v1/auth/login").permitAll()
                            .requestMatchers("/api/v1/welcome/patient").hasAnyRole("PATIENT","DOCTOR")
                            .requestMatchers("/api/v1/welcome/doctor").hasRole("DOCTOR")
                            .anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            CustomerUserDetailsService customerUserDetailsService,
            PasswordEncoder passwordEncoder

    ){
        DaoAuthenticationProvider provider  = new DaoAuthenticationProvider(customerUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;

    }
}
