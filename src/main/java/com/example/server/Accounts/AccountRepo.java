package com.example.server.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,String>{
boolean existsByEmail(String email);
    
}
