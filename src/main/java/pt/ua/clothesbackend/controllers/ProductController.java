package pt.ua.clothesbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.ua.clothesbackend.dto.CreateProductDto;
import pt.ua.clothesbackend.model.Product;
import pt.ua.clothesbackend.services.ProductService;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Product> addNewProduct(@RequestBody CreateProductDto productDto){
        return productService.createProduct(productDto);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
}
