package pt.ua.clothesbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pt.ua.clothesbackend.dto.CreateProductDto;
import pt.ua.clothesbackend.model.Product;
import pt.ua.clothesbackend.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<Product> createProduct(CreateProductDto productDto){
        Product newProduct = new Product();
        newProduct.setName(productDto.getName());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setCategory(productDto.getCategory());
        productRepository.save(newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<Iterable<Product>> getAllProducts(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Product> getProductById(Integer id){
        Optional<Product> findProduct = productRepository.findById(id);

        if(findProduct.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(findProduct.get(), HttpStatus.OK);
        }
    }

}
