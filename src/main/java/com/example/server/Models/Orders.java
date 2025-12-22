package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;
    private Long id;
    private String title;
    private String email;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private String description;
    private String image;
    private String category;
    private Long quantity;
}
