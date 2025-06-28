package com.example.server.Accounts;

import org.springframework.beans.factory.annotation.Autowired;
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
    Account saved=account.save(details);
    return ResponseEntity.ok(saved);
 }
}
