package com.example.darkshop.controller;

import com.example.darkshop.model.Product;
import com.example.darkshop.service.ProductService;
import com.example.darkshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/")
    public String allProducts(@RequestParam(name = "searchWord", required = false) String title, Principal principal
            , Model model) {
        model.addAttribute("products", productService.findAll(title));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "products";
    }

    @GetMapping("/products/{id}")
    public String Info(@PathVariable Integer id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/products")
    public String create(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                         @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/profile";
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {
        productService.delete(userService.getUserByPrincipal(principal), id);
        return "redirect:/profile";
    }
}
