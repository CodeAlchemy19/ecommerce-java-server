package com.example.server.Controller;

import com.example.server.Models.Cart;
import com.example.server.Models.Wishlist;
import com.example.server.Repositories.WishlistRepo;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private WishlistRepo repo;
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody Wishlist wishlist){
            if(repo.existsById(wishlist.getId())){
                return ResponseEntity.ok(Map.of("message","Already in the wishlist"));
            }
            repo.save(wishlist);
            return ResponseEntity.ok(Map.of("message","Item added to wishlist"));
    }
    @GetMapping("/get")
    public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String header){
        if(header==null || !header.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body(Map.of("message","Invalid token provided"));
        }
        String token=header.substring(7);
        String email=jwtUtil.extractUserName(token);

        Optional<List<Wishlist>> items=repo.findByEmail(email);
        System.out.println(items+" "+email);
        if(items.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","No products found in the cart for this email"));
        }
        return ResponseEntity.ok(items);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Item deleted from wishlist"));
    }
}
