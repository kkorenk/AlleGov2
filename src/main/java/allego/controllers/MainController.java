package allego.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "/index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }




}
