package allego.services.implementations;

import allego.models.Product;
import allego.models.User;
import allego.models.cart.CartItem;
import allego.models.cart.ProductToCartItem;
import allego.models.cart.ShoppingCart;
import allego.repositories.CartItemRepository;
import allego.repositories.ProductToCartItemRepository;
import allego.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
@Service
public class CartItemServiceImpl implements CartItemService{


    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductToCartItemRepository productToCartItemRepository;

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart){
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal qty = new BigDecimal(cartItem.getQuantity());
        BigDecimal bigDecimal = cartItem.getProduct().getPrice().multiply(qty);

        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);

        cartItem.setSubTotal(bigDecimal);

        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem addProductToCartItem(Product product, User user, int qty){
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for(CartItem cartItem : cartItemList){
            if(product.getId() == cartItem.getProduct().getId()){
                cartItem.setQuantity(cartItem.getQuantity()+qty);
                BigDecimal bigDecimal = new BigDecimal(qty);
                cartItem.setSubTotal(product.getPrice().multiply(bigDecimal));
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setProduct(product);
        cartItem.setQuantity(qty);
        BigDecimal bigDecimal = new BigDecimal(qty);
        cartItem.setSubTotal(product.getPrice().multiply(bigDecimal));
        cartItemRepository.save(cartItem);

        ProductToCartItem productToCartItem = new ProductToCartItem();
        productToCartItem.setProduct(product);
        productToCartItem.setCartItem(cartItem);
        productToCartItemRepository.save(productToCartItem);

        return cartItem;
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findOne(id);
    }
    @Override
    public void removeCartItem(CartItem cartItem){
        productToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }
}