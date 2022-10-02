package com.example.darkshop.service;

import com.example.darkshop.model.Product;
import com.example.darkshop.model.User;
import com.example.darkshop.repositori.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ImageService imageService;

    public List<Product> findAll(String title) {
        if (title != null) {
            return productRepository.findByTitleStartingWith(title);
        }
        return productRepository.findAll();
    }

    @Transactional
    public void saveProduct(Principal principal, Product product,
                            MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        imageService.savaImage(product, file1, file2, file3);
        product.setUser(userService.getUserByPrincipal(principal));
        productRepository.save(product);
    }

    public void delete(User user, Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        if (product.getUser().getId().equals(user.getId())) {
            productRepository.delete(product);
            log.info("Product with id = {} was deleted", id);
        } else {
            log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
        }
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }
}
