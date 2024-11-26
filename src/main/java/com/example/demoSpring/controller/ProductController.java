package com.example.demoSpring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSpring.models.Product;
import com.example.demoSpring.models.RepositoryObject;
import com.example.demoSpring.repositories.ProductRepository;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/v1/Products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAppProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<RepositoryObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new RepositoryObject("ok", "Query product successfully", foundProduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new RepositoryObject("fasle", "Cannot find product with id = " + id, ""));
        }
    }

    @PostMapping("/insert")
    ResponseEntity<RepositoryObject> insertProduct(@RequestBody Product product) {
        List<Product> foundProducts = productRepository.findByNameProduct(product.getNameProduct().trim());
        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new RepositoryObject("failed", "Product name already taken", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new RepositoryObject("ok", "Insert Product successfully", productRepository.save(product)));
    }

    // update, upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<RepositoryObject> updateProduct(@PathVariable Long id, @RequestBody Product newProduct) {

        Product updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setNameProduct(newProduct.getNameProduct());
                    product.setPrice(newProduct.getPrice());
                    product.setYear(newProduct.getYear());
                    product.setUrl(newProduct.getUrl());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    return productRepository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new RepositoryObject("ok", "Update product successfully", updateProduct));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<RepositoryObject> deleteProduct(@PathVariable Long id) {
        boolean exits = productRepository.existsById(id);
        if (exits) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new RepositoryObject("ok", "Delete product successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new RepositoryObject("failed", "Cannot found product to delete", id));

    }

}
