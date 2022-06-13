package pt.ua.clothesbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.clothesbackend.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
