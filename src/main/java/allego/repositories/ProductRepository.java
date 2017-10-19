package allego.repositories;

import allego.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ibm on 2017-10-17.
 */
public interface ProductRepository  extends CrudRepository<Product, Long> {
    Product findById(Long id);
    Product findByName(String name);
    List<Product> findAll();
}
