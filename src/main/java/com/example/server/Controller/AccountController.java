package com.example.server.Controller;

import java.util.List;
import java.util.Map;

import com.example.server.Models.Account;
import com.example.server.Repositories.AccountRepo;
import com.example.server.Services.AccountService;
import com.example.server.Utility.JwtUtil;
import com.example.server.config.PasswordEncoderConfig;
import com.example.server.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private AccountRepo repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    // ---------------- Sign In ----------------
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Account details) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(details.getEmail(), details.getPassword()));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        UserDetails userDetails = accountService.loadUserByUsername(details.getEmail());
        AuthResponse response = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(response);
    }

    // ---------------- Sign Up ----------------
    // Clear allUsers cache on new signup
    @PostMapping("/signup")

    public ResponseEntity<?> signUp(@RequestBody Account details) {
        boolean success = accountService.registerUser(details);
        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Account created successfully"));
    }

    // ---------------- Get User from Token ----------------
    @GetMapping("/details")
    @Cacheable(value = "userByEmail", key = "#authHeader.substring(7)")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token header");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUserName(token);

        return repo.findByEmail(email)
                .map(user -> {
                    user.setPassword(null); // hide password in response
                    return ResponseEntity.ok(user);
                })
                .orElse(null);
    }

    // ---------------- Fetch All Users ----------------
    @GetMapping("/fetchusers")
    @Cacheable(value = "allUsers")
    public List<Account> getAllUsers() {
        List<Account> users = repo.findAll();
        users.forEach(user -> user.setPassword(null)); // hide passwords
        return users;
    }

    // ---------------- Update Account ----------------
    // Clear caches when updating
    @PutMapping("/update/{email}")
    @CacheEvict(value = {"allUsers", "userByEmail"}, allEntries = true)
    public ResponseEntity<?> updateAccount(@PathVariable String email, @RequestBody Account updatedAccount) {
        return repo.findByEmail(email)
                .map(account -> {
                    account.setName(updatedAccount.getName());
                    account.setMobileNumber(updatedAccount.getMobileNumber());
                    account.setPassword(passwordEncoderConfig.passwordEncoder().encode(updatedAccount.getPassword()));
                    repo.save(account);
                    account.setPassword(null); // hide password
                    return ResponseEntity.ok(Map.of("message", "Account updated successfully", "account", account));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Account not found")));
    }

}
