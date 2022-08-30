package com.example.darkshop.repositories;

import com.example.darkshop.models.Image;
import com.example.darkshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleStartingWith(String title);
    List<Product> findByTitleEndingWith(String title);
    List<Product> findByTitleContaining(String title);
}
