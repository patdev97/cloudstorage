package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.data.UserCredentials;
import com.udacity.jwdnd.course1.cloudstorage.data.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.data.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private NoteService noteService;
    private CredentialsService credentialsService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialsService credentialsService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String showHomepage(Model model, Authentication authentication, UserNoteForm userNoteForm, UserCredentialsForm userCredentialsForm) {
        String username = authentication.getName();
        User user = userService.getUser(username);
        Integer userId = user.getUserId();

        List<UserFile> userFiles = fileService.getUserFiles(userId);
        if (!userFiles.isEmpty()) {
            model.addAttribute("files", userFiles);
        }

        List<UserNote> userNotes = noteService.getUserNotes(userId);
        if(!userNotes.isEmpty()) {
            model.addAttribute("notes", userNotes);
        }

        List<UserCredentials> userCredentials = credentialsService.getUserCredentials(userId);
        if(!userCredentials.isEmpty()) {
            model.addAttribute("credentials", userCredentials);
        }

        return "home";
    }
}
