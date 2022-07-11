package com.example.buysell.controller;

import com.example.buysell.models.Product;
import com.example.buysell.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }


    @GetMapping
    public String product(Model model){
        model.addAttribute("product" , productService.ListProducts());
    return "product";
    }

    @GetMapping ("/product/{id}")
    public String productInfo(@PathVariable Long id , Model model){
        model.addAttribute("product" , productService.getProductById(id));
    return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(Product product){
        productService.saveProduct(product);
        return "redirect:/";
    }


    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/";

    }
}
