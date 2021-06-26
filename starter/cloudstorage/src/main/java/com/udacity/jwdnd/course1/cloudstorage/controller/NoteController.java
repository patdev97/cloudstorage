package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserNoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String note(UserNoteForm userNoteForm, Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        String message;
        if(userNoteForm.getNoteId() > 0) {
            message = noteService.addNote(userNoteForm, userId, true);
        } else {
            message = noteService.addNote(userNoteForm, userId, false);
        }
        if (message == null) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("errorMessage", message);
        }
        return "result";
    }

    @GetMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable(value = "noteId") Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("success", true);
        return "result";
    }

}
