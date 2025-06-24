package com.example.server.Accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String>{
Optional<Account> findByEmail(String email);
Optional<Account> findByEmailAndPassword(String email,String password);
boolean existsByEmail(String email);
}
