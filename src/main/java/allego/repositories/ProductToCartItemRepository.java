package allego.repositories;

import allego.models.Product;
import allego.models.cart.ProductToCartItem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ibm on 2017-11-06.
 */
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long>{

}
