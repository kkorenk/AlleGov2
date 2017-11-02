package allego.services.implementations;

import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;
import allego.repositories.ShoppingCartRepository;
import allego.services.CartItemService;
import allego.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository

    public void updateShoppingCart(ShoppingCart shoppingCart){
        BigDecimal cartTotal = new BigDecimal(0);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList){
            if(cartItem.getProduct().getQuantity() >0){
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal.add(cartItem.getSubTotal());
            }
        }

        shoppingCart.setTotal(cartTotal);
        shoppingCartRepository.save(shoppingCart);


    }

}
