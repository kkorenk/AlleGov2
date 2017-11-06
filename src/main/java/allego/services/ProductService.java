package allego.services;

import allego.models.Product;

import java.util.List;

/**
 * Created by ibm on 2017-10-17.
 */
public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product findById(Long id);
}
