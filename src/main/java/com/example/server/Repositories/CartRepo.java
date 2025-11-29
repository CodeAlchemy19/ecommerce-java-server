package com.example.server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long>{

    Optional<List<Cart>> findByEmail(String email);
}
