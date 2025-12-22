package com.example.server.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Products implements Serializable {
    @Id
    private Long id;
    private String title;
    private Double price;
    private Double discountPrice;
    private Double rating;
    private String description;
    private String image;
    private String category;
    private Long quantity;


}
