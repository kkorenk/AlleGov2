package allego.controllers;


import allego.models.User;
import allego.security.PasswordResetToken;
import allego.services.UserService;
import allego.services.implementations.UserSecurityService;
import allego.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping("/")
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){

        return "forgetPassword";
    }



    @RequestMapping(value = "/register")
    public String register(Locale locale,@RequestParam("token") String token, Model model) {
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

        return "/register";
    }


}
