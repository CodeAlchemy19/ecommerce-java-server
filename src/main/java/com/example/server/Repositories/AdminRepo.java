package com.example.server.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Admin;



public interface AdminRepo extends JpaRepository<Admin,Long>{

    Optional<Admin> findByEmail(String email);
Optional<Admin> findByEmailAndPassword(String email,String password);
boolean existsByEmail(String email);
}