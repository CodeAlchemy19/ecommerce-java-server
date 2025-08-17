package com.example.server.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
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

    public Products() {
    }

    public Products(Long id, String title, Double price, Double discountPrice, Double rating, String description,
            String image, String category, Long quantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.discountPrice = discountPrice;
        this.rating = rating;
        this.description = description;
        this.image = image;
        this.category = category;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
