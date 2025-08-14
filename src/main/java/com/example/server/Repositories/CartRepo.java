package com.example.server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Cart;

public interface CartRepo extends JpaRepository<Cart,Long>{

    
}
