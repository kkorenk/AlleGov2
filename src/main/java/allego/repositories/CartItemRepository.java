package allego.repositories;

import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
