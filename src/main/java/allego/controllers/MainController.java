package allego.controllers;


import allego.models.User;
import allego.security.PasswordResetToken;
import allego.security.Role;
import allego.security.UserRole;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

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

    @RequestMapping("/")
    public String index(){
        return "/index";
    }

    // account stuff
    //TODO przeniesc do user/ jak zrobione bedzie logowanie
    @RequestMapping("/myAccount")
    public String myAccount(){
        return "/myAccount";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "/login";
    }
    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model){
        model.addAttribute("classActiveForgetPassword", true);
        return "forgetPassword";
    }

    @RequestMapping("/register")
    public String register(){
        return "/register";
    }

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public String registerPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model) throws Exception{

        model.addAttribute("classActiveNewAccount", true);
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
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }
        User user = passwordResetToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "/confirm";
    }

    @RequestMapping(value = "/products")
    public String products() {
        return "/products";
    }

    @RequestMapping(value = "/admin/addProduct")
    public String addProduct() {
        return "/admin/addProduct";
    }






}
