package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.User;
import io.mountblue.blog_application_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("userForm") User user, HttpServletRequest request) {
        if (userService.checkValidity(user.getEmail())) {
            return "redirect:/login?userExistsError";
        }
        user.setRole("ROLE_AUTHOR");
        user.setPassword("{noop}" + user.getPassword());
        userService.createUser(user);
        return "redirect:/login";
    }
}
