package org.example.stockdatamonitoring.controller;

import org.example.stockdatamonitoring.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal != null) {
            String name = principal.getAttribute("name");
            String email = principal.getAttribute("email");
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            userService.saveUser(name, email);
        }
        return "home";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login?error")
    public String errorLogin() {
        return "Login failed, please try again.";
    }
}

