package com.example.darkshop.service;

import com.example.darkshop.model.Image;
import com.example.darkshop.model.Product;
import com.example.darkshop.repositori.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.darkshop.utill.ImageUtils.toImageEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ProductRepository productRepository;

    public void savaImage(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;

        if (file1.getSize() == 0 && file2.getSize() == 0 && file3.getSize() == 0) {
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
