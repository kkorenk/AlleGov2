package allego.repositories;

import allego.models.Product;
import allego.models.cart.CartItem;
import allego.models.cart.ProductToCartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long>{

    void deleteByCartItem(CartItem cartItem);
}
