package com.example.server.Controller;

import com.example.server.Models.Orders;
import com.example.server.Models.Wishlist;
import com.example.server.Repositories.OrderRepository;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderControlller {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OrderRepository orderRepository;
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody Orders order){
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("message","Order saved to database"));
    }
    @GetMapping("/get")
    public ResponseEntity<?> getProducts(@RequestHeader("Authorization") String header){
        if(header==null || !header.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body(Map.of("message","Invalid token provided"));
        }
        String token=header.substring(7);
        String email=jwtUtil.extractUserName(token);

        Optional<List<Orders>> items=orderRepository.findByEmail(email);
        System.out.println(items+" "+email);
        if(items.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","No products found in the cart for this email"));
        }
        return ResponseEntity.ok(items);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        orderRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Item deleted from orders"));
    }
}
