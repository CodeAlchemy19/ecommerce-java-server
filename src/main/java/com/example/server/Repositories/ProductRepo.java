package com.example.server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Products;

public interface ProductRepo extends JpaRepository<Products,Long>{

    
}