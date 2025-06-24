package com.example.server.Accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String>{
boolean existsByEmail(String email);
Optional<Account> findByEmailAndPassword(String email,String password);
    
}
