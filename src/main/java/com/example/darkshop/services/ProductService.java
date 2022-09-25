package com.example.darkshop.services;

import com.example.darkshop.models.Image;
import com.example.darkshop.models.Product;
import com.example.darkshop.models.User;
import com.example.darkshop.repositories.ProductRepository;
import com.example.darkshop.repositories.UserRepository;
import com.example.darkshop.utill.ImageUtils;
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
public class ProductService extends ImageUtils  {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> findAll(String title) {
        if (title != null) {
            return productRepository.findByTitleStartingWith(title);
        } else if (title != null) {
            return productRepository.findByTitleEndingWith(title);
        }else if (title != null) {
            return productRepository.findByTitleContaining(title);
        }else {
            return productRepository.findAll();
        }
    }


    @Transactional
    public void save(Principal principal, Product product){
        product.setUser(getUserByPrincipal(principal));
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
    
    public void delete(User user, Integer id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void savaImage(Product product,MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException  {
        Image image1;
        Image image2;
        Image image3;

        if(file1.getSize() == 0 && file2.getSize() == 0 && file3.getSize() == 0){
            Image image4 = new Image();
            image4.setOriginalFileName("no_photo.jpg");
            product.addImageToProduct(image4);
            }
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
    }

}
