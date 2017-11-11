package allego.repositories;

import allego.models.cart.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Kamil on 02.11.2017.
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart,Long>{

}
