package allego.controllers;


import allego.models.Product;
import allego.models.User;
import allego.security.PasswordResetToken;
import allego.security.Role;
import allego.security.UserRole;
import allego.services.ProductService;
import allego.services.UserService;
import allego.services.implementations.UserSecurityService;
import allego.utility.MailConstructor;
import allego.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String index(){
        return "/index";
    }

    // account stuff
    @RequestMapping(value = "/user/myAccount", method = RequestMethod.GET)
    public String myAccount(@ModelAttribute("user") User user, Model model){ return "/user/myAccount"; }

    @RequestMapping("/login")
    public String login() { return "/login"; }

    @RequestMapping(value = "/forgetPassword")
    public String forgetPassword(){return "forgetPassword";}

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public String forgetPasswordPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            Model model){

        User user = userService.findByEmail(userEmail);
        if(user == null){
            model.addAttribute("emailNotExists", true);
            return "forgetPassword";
        }


        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user,token);

        String appUrl ="http://" +request.getServerName()+":" + request.getServerPort() +request.getContextPath();
        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("recoveryEmailSent", "true");
        return "forgetPassword";
    }

    @RequestMapping("/register")
    public String register(){
        return "/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model) throws Exception{


        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if(userService.findByUsername(username) !=null){
            model.addAttribute("usernameExists", true);
            return "register";
        }

        if(userService.findByEmail(userEmail) != null){
            model.addAttribute("emailExists", true);
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user,token);

        String appUrl ="http://" +request.getServerName()+":" + request.getServerPort() +request.getContextPath();
        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        mailSender.send(email);

        model.addAttribute("emailSent", "true");

        return "/register";

    }


    @RequestMapping(value = "/confirm")
    public String confirm(Locale locale,@RequestParam("token") String token, Model model) {


        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);

        if(passwordResetToken == null){
            String message = "Ïnvalid token.";
            model.addAttribute("errorMessage", message);
            return "/confirm";
        }

        User user = passwordResetToken.getUser();
        user.setEnabled(true);
        userService.save(user);
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String message = "Your account has been activated succesfully.";
        model.addAttribute("successMessage", message);

        return "/confirm";
    }

/*
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirmPost(@RequestParam("token") String token,@ModelAttribute("signup-password-confirm") String password, Model model) {

        PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        User user = passwordResetToken.getUser();//TODO tu jest blad przy ustawianiu nowego hasła

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("passwordChanged",true);
        return "/confirm?token=0";
    }
*/

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(Model model) {

        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "/products";
    }



    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
    public String addProductPost(@ModelAttribute("name") String name,
                             @ModelAttribute("price") BigDecimal price,
                             @ModelAttribute("quantity") int quantity,
                             @ModelAttribute("description") String description,
                             Model model) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);

        if(productService.createProduct(product) != null){
            model.addAttribute("productAdded",true);
        }
        else{
            model.addAttribute("productFailure",true);
        }
        return "/admin/panel";
    }

    @RequestMapping(value = "/admin/panel", method = RequestMethod.GET)
    public String adminPanel(Model model) {
        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);
        return "/admin/panel";
    }


}
