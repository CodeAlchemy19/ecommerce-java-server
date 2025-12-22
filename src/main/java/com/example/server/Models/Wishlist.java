package com.example.server.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @Id

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
