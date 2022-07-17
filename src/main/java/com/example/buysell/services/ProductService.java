package com.example.buysell.services;

import com.example.buysell.models.Product;
import com.example.buysell.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
    public class ProductService {
    private static final Logger log = LogManager.getLogger(ProductService.class);

        private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listProducts(String title) {
            if (title != null) return productRepository.findByTitle(title);
            return productRepository.findAll();
        }

        public void saveProduct(Product product) {
            log.info("Saving new {}"+ product);
            productRepository.save(product);
        }

        public void deleteProduct(Long id) {
            productRepository.deleteById(id);
        }

        public Product getProductById(Long id) {
            return productRepository.findById(id).orElse(null);
    }
}
