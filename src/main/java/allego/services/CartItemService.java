package allego.services;

import allego.models.Product;
import allego.models.User;
import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;

import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    CartItem updateCartItem(CartItem cartItem);
    CartItem addProductToCartItem(Product product, User user,int qty);
}
