package com.example.server.Controller;

import com.example.server.Models.Order;
import com.example.server.Repositories.OrderRepository;
import com.example.server.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OrderRepository orderRepository;

    // ---------------- ADD ORDER ----------------
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        orderRepository.save(order);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Order saved to database"));
    }

    // ---------------- GET ORDERS BY USER ----------------
    @GetMapping("/get")
    public ResponseEntity<?> getOrders(
            @RequestHeader("Authorization") String header) {

        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Invalid token provided"));
        }

        String token = header.substring(7);
        String email = jwtUtil.extractUserName(token);

        List<Order> orders = orderRepository.findByEmail(email);

        // ✔ empty list is VALID
        return ResponseEntity.ok(orders);
    }

    // ---------------- DELETE ORDER ----------------
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {

        if (!orderRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Order not found"));
        }

        orderRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Order deleted successfully"));
    }
}
