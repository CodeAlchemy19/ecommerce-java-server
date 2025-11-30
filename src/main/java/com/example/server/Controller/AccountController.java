package com.example.server.Controller;

import java.util.List;
import java.util.Map;

import com.example.server.Models.Account;
import com.example.server.Repositories.AccountRepo;
import com.example.server.Services.AccountService;
import com.example.server.Utility.JwtUtil;
import com.example.server.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private AccountRepo repo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Account details) {

        // Encode password here


        boolean success = accountService.registerUser(details);
        if (!success) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Account created successfully"));
    }

    // ---------------- Get User From Token ----------------
    @GetMapping("/details")
    public ResponseEntity<?> getUserFromToken(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token header"));
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUserName(token);

        var optionalUser = repo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        Account user = optionalUser.get();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }



    // ---------------- Fetch All Users ----------------
    @GetMapping("/fetchusers")

    public List<Account> getAllUsers() {
        List<Account> users = repo.findAll();
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    // ---------------- Update Account ----------------
    @PutMapping("/update")

    public ResponseEntity<?> updateAccount(@RequestParam("email") String email,
                                           @RequestBody Account updatedAccount) {

        return repo.findByEmail(email)
                .map(account -> {
                    if (updatedAccount.getName() != null) {

                    account.setName(updatedAccount.getName());
                    }
                    if(updatedAccount.getMobileNumber()!=null){

                    account.setMobileNumber(updatedAccount.getMobileNumber());
                    }

                    if (updatedAccount.getPassword() != null) {
                        account.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
                    }

                    repo.save(account);
                    account.setPassword(null);

                    return ResponseEntity.ok(
                            Map.of("message", "Account updated successfully", "account", account)
                    );
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Account not found")));
    }
    //-----------------Check email before sending otp--------
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email){
        boolean emailExists=repo.existsByEmail(email);
        if(emailExists){
           return ResponseEntity.badRequest().body(Map.of("message","User already existed with the email"));
        }
        return ResponseEntity.ok().body(Map.of("message","User allowed to signup"));
    }
    // ---------------- Delete User ----------------
    @DeleteMapping("/delete")

    public ResponseEntity<?> deleteUser(@RequestParam String email) {

        int deleted = repo.deleteByEmailCustom(email);

        if (deleted > 0) {
            return ResponseEntity.ok("User deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Email id not exists");
    }
}
