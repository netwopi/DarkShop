package com.example.darkshop.repositories;


import com.example.darkshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByTitleStartingWith(String title);
    List<Product> findByTitleEndingWith(String title);
    List<Product> findByTitleContaining(String title);
}
