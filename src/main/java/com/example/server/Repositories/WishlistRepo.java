package com.example.server.Repositories;

import com.example.server.Models.Wishlist;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepo extends JpaRepository<Wishlist,Long> {
    boolean existsById(Long id);
    Optional<List<Wishlist>> findByEmail(String email);
}
