package allego.services.implementations;

import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;
import allego.repositories.CartItemRepository;
import allego.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {

    }
}