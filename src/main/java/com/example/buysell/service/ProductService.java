package com.example.buysell.service;

import com.example.buysell.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private long ID=0;
    {
        products.add(new Product(++ID,"Iphone" , "perfect condition",23,"Minsk","Igor"));
        products.add(new Product(++ID,"Ipad" , "bad condition",56,"Minsk","Igor"));
        products.add(new Product(++ID,"Apple Watch" , "incognito",33,"Minsk","Slava"));
        products.add(new Product(++ID,"Ipad" , "bad condition",13,"Minsk","Solomon"));
    }

    public List<Product> ListProducts() {
        return products;
    }
    public void saveProduct(Product product){
        product.setId(++ID);
        products.add(product);
    }
    public void deleteProduct(Long id){
        products.removeIf(product -> product.getId()==id);
    }

    public Product getProductById(Long id) {
        for (Product product : products){
            if (product.getId()==id);
                return product;
        }
        return null;
    }
}
