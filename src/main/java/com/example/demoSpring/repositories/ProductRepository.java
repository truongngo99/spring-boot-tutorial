package com.example.demoSpring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoSpring.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameProduct(String nameProduct);
} 