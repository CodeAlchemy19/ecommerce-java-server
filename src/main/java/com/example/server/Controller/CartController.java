package com.example.server.Controller;
import java.util.*;

import com.example.server.Filter.JwtFilter;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.server.Models.Cart;
import com.example.server.Repositories.CartRepo;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
   private CartRepo cart;
   @PostMapping("/add")
public ResponseEntity<?> addToCart(@RequestBody Cart item) {
    Optional<Cart> existing = cart.findById(item.getId());

    if (existing.isPresent()) {
        Cart cartItem = existing.get();
        cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
        cart.save(cartItem);
        return ResponseEntity.ok(Map.of("message","Quantity updated"));
    } else {
        cart.save(item);
        return ResponseEntity.ok().body(Map.of("message","Item added to cart"));
    }
}

   @GetMapping("/get")
   public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String header){
       if(header==null || !header.startsWith("Bearer ")){
           return ResponseEntity.badRequest().body(Map.of("message","Invalid token provided"));
       }
       String token=header.substring(7);
       String email=jwtUtil.extractUserName(token);

       Optional<List<Cart>> items=cart.findByEmail(email);
       if(items.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","No products found in the cart for this email"));
       }
       return ResponseEntity.ok(items);
   }
   @PutMapping("/update")
public ResponseEntity<?> updateProduct(@RequestBody Cart item) {
    Optional<Cart> existingCartItem = cart.findById(item.getId());

    if (existingCartItem.isPresent()) {
        Cart cartItem = existingCartItem.get();
        cartItem.setQuantity(item.getQuantity());
        cart.save(cartItem);
        return ResponseEntity.ok("Cart item updated");
    } else {
        return ResponseEntity.badRequest().body("Cart item not found");
    }
}

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<?> deleteProduct(@PathVariable Long id){
    cart.deleteById(id);
    return ResponseEntity.ok("Deleted");
   }
}
