package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Long id; // frontend generated OR backend generated

    private String name;
    private String email;

    private String orderDate;
    private String expectedDelivery;

    private String status;
    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id") // FK in order_items table
    private List<OrderItem> items;
}
