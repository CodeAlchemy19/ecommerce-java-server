package com.example.server.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.server.Models.Products;
import com.example.server.Repositories.ProductRepo;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepo product;

    // 🟢 Add products and clear cache
    @PostMapping("/add")
    @CacheEvict(value = { "allProducts", "productById" }, allEntries = true)
    public ResponseEntity<?> addProduct(@RequestBody List<Products> products) {
        List<Products> saved = product.saveAll(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 🟢 Get all products (cached)
    @GetMapping("/get")
    @Cacheable(value = "allProducts")
    public List<Products> getProduct() {
        System.out.println("Fetching from DB..."); // to verify caching
        return product.findAll();
    }

    // 🟢 Get product by ID (cached per ID)
    @GetMapping("/get/{id}")
    @Cacheable(value = "productById", key = "#id")
    public Products getProductById(@PathVariable Long id) {
        System.out.println("Fetching product ID " + id + " from DB...");
        return product.findById(id).orElse(null);
    }

    // 🟠 Update product and clear related caches
    @PutMapping("/update/{id}")
    @CacheEvict(value = { "allProducts", "productById" }, allEntries = true)
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Products updateProducts) {
        Products products = product.findById(id).orElse(null);
        if (products != null) {
            products.setTitle(updateProducts.getTitle());
            products.setPrice(updateProducts.getPrice());
            products.setDiscountPrice(updateProducts.getDiscountPrice());
            products.setDescription(updateProducts.getDescription());
            products.setImage(updateProducts.getImage());
            products.setCategory(updateProducts.getCategory());
            products.setQuantity(updateProducts.getQuantity());
            product.save(products);
            return ResponseEntity.ok("Updated product");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating product");
    }

    // 🔴 Delete product and clear caches
    @DeleteMapping("/delete/{id}")
    @CacheEvict(value = { "allProducts", "productById" }, allEntries = true)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!product.existsById(id)) {
            return ResponseEntity.badRequest().body("Id not found");
        }
        product.deleteById(id);
        return ResponseEntity.ok("Product deleted");
    }
}
