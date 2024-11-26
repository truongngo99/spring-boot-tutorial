package com.example.demoSpring.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameProduct;
    private int yearProduct;
    private Double price;
    private String url;

    public Product() {}

    public Product(String nameProduct, int year, Double price, String url) {
        this.nameProduct = nameProduct;
        this.yearProduct = year;
        this.price = price;
        this.url = url;
    }
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return this.nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getYear() {
        return this.yearProduct;
    }

    public void setYear(int year) {
        this.yearProduct = year;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nameProduct='" + getNameProduct() + "'" +
            ", year='" + getYear() + "'" +
            ", price='" + getPrice() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}


