package com.example.server.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    private Long id; // product id

    private String title;
    private String email;

    private Double price;
    private Double discountPrice;
    private Integer quantity;

    private String image;
}
