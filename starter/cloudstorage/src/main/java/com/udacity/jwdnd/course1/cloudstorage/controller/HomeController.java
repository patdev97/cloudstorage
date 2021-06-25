package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.data.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private FileService fileService;
    private UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String showHomepage(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUser(username);

        List<UserFile> userFiles = fileService.getUserFiles(user.getUserId());
        if (!userFiles.isEmpty()) {
            model.addAttribute("files", userFiles);
        }

        return "home";
    }
}
