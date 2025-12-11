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

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    // ❌ Remove the global Admin variable
    // Admin admin;   // REMOVE THIS


    public UserDetails loadByUsername(String email) throws UsernameNotFoundException {

        // ✔ Load admin fresh every time
        Admin admin = adminRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword()) // hashed password stored
                .roles("ADMIN")
                .build();
    }

    public boolean registerUser(Admin admin) {
        if (adminRepo.existsByEmail(admin.getEmail())) {
            return false;
        }

        admin.setId(null);

        // ✔ Encode password before saving
        admin.setPassword(passwordEncoderConfig.passwordEncoder().encode(admin.getPassword()));

        adminRepo.save(admin);
        return true;
    }
}
