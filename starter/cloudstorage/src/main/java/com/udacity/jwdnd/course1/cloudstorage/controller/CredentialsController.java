package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    private CredentialsService credentialsService;
    private UserService userService;

    public CredentialsController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @PostMapping("/credentials")
    public String credentials(UserCredentialsForm userCredentialsForm, Model model, Authentication authentication) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        String message;
        if (userCredentialsForm.getCredentialId() > 0) {
            message = credentialsService.addCredentials(userCredentialsForm, userId, true);
        } else {
            message = credentialsService.addCredentials(userCredentialsForm, userId, false);
        }
        if (message == null) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "result";
    }

    @GetMapping("/credential-delete/{credentialId}")
    public String deleteCredential(@PathVariable(value = "credentialId") Integer credentialId, Model model) {
        credentialsService.deleteCredentials(credentialId);
        model.addAttribute("success", true);
        return "result";
    }
}
