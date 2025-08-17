package com.example.server.Controller;

import java.util.*;

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

    // --------------------------- SIGN IN ---------------------------
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody Account details) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(details.getEmail(), details.getPassword()));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }

        UserDetails userDetails = accountService.loadUserByUsername(details.getEmail());
        AuthResponse response = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(response);
    }

    // --------------------------- SIGN UP ---------------------------
    @PostMapping("/signup")
    @CacheEvict(value = {"allUsers", "userByEmail"}, allEntries = true)
    public ResponseEntity<?> signUp(@RequestBody Account details) {
        boolean success = accountService.registerUser(details);
        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email already exists"));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Account created successfully"));
    }

    // --------------------------- GET USER DETAILS ---------------------------
    @GetMapping("/details")
    @Cacheable(value = "userByEmail", key = "#email")
    public ResponseEntity<?> getUserFromToken(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String emailParam // fallback for direct call
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token header"));
        }

        String token = authHeader.substring(7);
        String email = (emailParam != null) ? emailParam : jwtUtil.extractUserName(token);

        return repo.findByEmail(email)
                .map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body((Account) Map.of("error", "User not found")));
    }

    // --------------------------- FETCH ALL USERS ---------------------------
    @GetMapping("/fetchusers")
    @Cacheable(value = "allUsers")
    public ResponseEntity<?> getAllUsers() {
        List<Account> users = repo.findAll();
        users.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(users);
    }

    // --------------------------- UPDATE ACCOUNT ---------------------------
    @PutMapping("/update/{email}")
    @CacheEvict(value = {"allUsers", "userByEmail"}, allEntries = true)
    public ResponseEntity<?> updateAccount(@PathVariable String email, @RequestBody Account updatedAccount) {
        return repo.findByEmail(email)
                .map(account -> {
                    account.setName(updatedAccount.getName());
                    account.setMobileNumber(updatedAccount.getMobileNumber());

                    if (updatedAccount.getPassword() != null && !updatedAccount.getPassword().isBlank()) {
                        account.setPassword(passwordEncoderConfig.passwordEncoder().encode(updatedAccount.getPassword()));
                    }

                    repo.save(account);
                    account.setPassword(null);
                    return ResponseEntity.ok(Map.of(
                            "message", "Account updated successfully",
                            "account", account
                    ));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Account not found")));
    }
}
