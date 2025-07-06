package com.example.server.config;

import com.example.server.Accounts.AccountService;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    @Autowired
    private AccountService accountService;
    @Autowired
    @Lazy
    private JwtUtil jwtUtil;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signin","auth/signup","/cart/**", "/products/**").permitAll()     // 👈 allow public access
                       
                        .anyRequest().authenticated()

                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cofig) throws Exception{
        return cofig.getAuthenticationManager();
    }

}
