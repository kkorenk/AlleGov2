package allego.services;

import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;

import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    void updateCartItem(CartItem cartItem);
}
