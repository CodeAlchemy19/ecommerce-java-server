package com.example.server.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Account;

public interface AccountRepo extends JpaRepository<Account,String>{
Optional<Account> findByEmail(String email);
Optional<Account> findByEmailAndPassword(String email,String password);
boolean existsByEmail(String email);
}
