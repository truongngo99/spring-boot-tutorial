package com.example.demoSpring.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demoSpring.models.Product;
import com.example.demoSpring.repositories.ProductRepository;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                Product iPhone15 = new Product( "iPhone 15 pro max", 2023, 23000.0,"");
                Product iPhone16 = new Product( "iPhone 16 pro max", 2024, 24000.0,"");
                logger.info("insert data:"+productRepository.saveAndFlush(iPhone15));
                logger.info("insert data:"+productRepository.saveAndFlush(iPhone16));
            }
        
        };
    }
}
