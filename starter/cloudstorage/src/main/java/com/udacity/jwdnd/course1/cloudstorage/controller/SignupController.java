package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showSignup() {
        return "signup";
    }

    @PostMapping
    public String signup(Model model, @ModelAttribute User user) {
        String errorSignup = null;

        if(!userService.isUsernameAvailable(user.getUsername())) {
            errorSignup = "The chosen username is not available.";
        }

        if(errorSignup == null) {
            int result = userService.createUser(user);
            if(result < 0) {
                errorSignup = "There was an error signing up. Please try again.";
            }
        }

        if(errorSignup == null) {
            model.addAttribute("successSignup", true);
        } else {
            model.addAttribute("errorSignup", errorSignup);
        }

        return "signup";
    }

}
