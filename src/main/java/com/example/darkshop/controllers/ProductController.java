package com.example.darkshop.controllers;

import com.example.darkshop.models.Product;
import com.example.darkshop.models.User;
import com.example.darkshop.services.ProductService;
import com.example.darkshop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String allProducts(@RequestParam(name = "searchWord", required = false) String title, Principal principal
            , Model model) {
        model.addAttribute("products", productService.findAll(title));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String Info(@PathVariable Integer id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String create(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.savaImage(product,file1, file2, file3);
        productService.save(principal, product);
        return "redirect:/profile";
    }

    @PostMapping("/product/delete/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {
        productService.delete(productService.getUserByPrincipal(principal), id);
        return "redirect:/profile";
    }

    @GetMapping("/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "my-products";
    }
}
