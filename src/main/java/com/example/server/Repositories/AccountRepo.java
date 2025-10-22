package com.example.server.Repositories;

import com.example.server.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, String> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM Account a WHERE a.email = :email")
    int deleteByEmailCustom(String email);
}

