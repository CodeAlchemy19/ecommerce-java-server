package com.example.server.Admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.Utility.JwtUtil;
import com.example.server.dto.AuthResponse;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/signin")
    public ResponseEntity<?> sigin(@RequestBody Admin admin){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword()));
        } catch (BadCredentialsException e) {
            // TODO: handle exception
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        UserDetails user=adminService.loadByUsername(admin.getEmail());
        AuthResponse response=jwtUtil.generateToken(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Admin admin){
        boolean existEmail=adminService.registerUser(admin);
        if(!existEmail){
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Account created successfully"));
    }
    @GetMapping("/details")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String authheader){
        if(authheader==null || !authheader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("Invalid token header");
        }
        String token=authheader.substring(7);
        String email=jwtUtil.extractUserName(token);
        return adminRepo.findByEmail(email).map(user->ResponseEntity.ok().body(user)).orElse(null);
    }
}
