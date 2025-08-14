package com.example.server.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.Models.Products;
import com.example.server.Repositories.ProductRepo;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepo product;
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody List<Products> products){
        List<Products> saved=product.saveAll(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(List.of(saved));
    }
    @GetMapping("/get")
    public List<Products> getProduct(){
        return product.findAll();
    }
    @GetMapping("/get/{id}")
    public List<Products> getProductById(@PathVariable Long id){
        return List.of(product.findById(id).orElse(null));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody Products updateProducts){
        Products products=product.findById(id).orElse(null);
        if(products!=null){
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error to update");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        if(!product.existsById(id)){
           
            
            return ResponseEntity.badRequest().body("Id not found");
        }
         product.deleteById(id);
        return ResponseEntity.ok().body("Product deleted");
    }
}
