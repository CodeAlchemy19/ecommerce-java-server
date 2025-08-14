package com.example.server.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.server.Models.Admin;
import com.example.server.Repositories.AdminRepo;
import com.example.server.config.PasswordEncoderConfig;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    Admin admin;
    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;
    public UserDetails loadByUsername(String email) throws UsernameNotFoundException{
        admin=adminRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.builder()
        .username(admin.getEmail())
        .password(admin.getPassword())
        .roles("ADMIN")
        .build();
    }
    public boolean registerUser(Admin admin){
        if(adminRepo.existsByEmail(admin.getEmail())){
            return false;
        }
        admin.setId(null);
        admin.setPassword(passwordEncoderConfig.passwordEncoder().encode(admin.getPassword()));
        adminRepo.save(admin);
        return true;
    }
}
