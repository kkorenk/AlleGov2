package allego.controllers;

import allego.models.Product;
import allego.models.User;
import allego.models.cart.CartItem;
import allego.models.cart.ShoppingCart;
import allego.services.CartItemService;
import allego.services.ProductService;
import allego.services.ShoppingCartService;
import allego.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

/**
 * Created by Kamil on 02.11.2017.
 */
@Controller
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/user/shoppingCart", method = RequestMethod.GET)
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCart = shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "/user/shoppingCart";
    }

    @RequestMapping("/user/addItem")
    public String addItem( @ModelAttribute("product") Product product, @ModelAttribute("qty") String qty, Model model, Principal principal){

        User user = userService.findByUsername(principal.getName());
        product = productService.findById(product.getId());

        if(Integer.parseInt(qty) > product.getQuantity()){
            model.addAttribute("nStock",true);
            return "forward:/product?id=" + product.getId();
        }

        CartItem cartItem = cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));
        model.addAttribute("addProductSuccess", true);

        return "forward:/product?id=" + product.getId();
    }

    @RequestMapping(value = "/user/updateCartItem", method = RequestMethod.POST)
    public String updateCartItem(@ModelAttribute("id") Long id, @ModelAttribute("qty") int qty, Model model, Principal principal){
        CartItem cartItem = cartItemService.findById(id);
        cartItem.setQuantity(qty);
        cartItemService.updateCartItem(cartItem);

        return shoppingCart(model, principal);
    }

    @RequestMapping("/user/removeItem")
    public String removeItem(@ModelAttribute("id") Long id, Model model, Principal principal){

        cartItemService.removeCartItem(cartItemService.findById(id));

        return shoppingCart(model, principal);
    }
}

