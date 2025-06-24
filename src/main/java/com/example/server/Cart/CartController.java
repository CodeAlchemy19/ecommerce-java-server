package com.example.server.Cart;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
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
   public List<Cart> getProducts(){
    return cart.findAll();
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
