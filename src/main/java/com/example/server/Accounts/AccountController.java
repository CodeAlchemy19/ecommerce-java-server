package com.example.server.Accounts;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
 private AccountRepo account;
 @PostMapping("/signin")
 public ResponseEntity<?> signIn(@RequestBody Account details){
   Account accounts=account.findByEmailAndPassword(details.getEmail(),details.getPassword()).orElse(null);
    if(accounts!=null){
       return ResponseEntity.ok(accounts);
    }
    return ResponseEntity.badRequest().body("null");
 } 
 @PostMapping("/signup")
 public ResponseEntity<?> signUp(@RequestBody Account details){
    Optional<Account> existingEmail=account.findByEmail(details.getEmail());
    if(existingEmail.isPresent()){
        return (ResponseEntity<?>) ResponseEntity.status(400);
    }
    Account saved=account.save(details);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message",saved));
 }
@PostMapping("/details")

public ResponseEntity<?> getDetails(@RequestBody Map<String, String> payload) {
    String email = payload.get("email").trim(); // trim to remove extra spaces
    Optional<Account> accounts = account.findByEmail(email);

    if (accounts.isPresent()) {
        return ResponseEntity.ok(accounts.get());
    } else {
        return ResponseEntity.status(404).body("Account not found with email: " + email);
    }
}




}
