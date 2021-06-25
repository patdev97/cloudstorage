package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/file-upload")
public class FileUploadController {

    private UserService userService;
    private FileService fileService;

    public FileUploadController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Principal principal) throws IOException {

        String errorMessage = null;

        if (fileUpload.isEmpty()) {
            errorMessage = "Please select a file that is not empty.";
        } else {
            File file = new File();

            file.setFileName(fileUpload.getOriginalFilename());
            file.setContentType(file.getContentType());
            file.setFileSize(fileUpload.getSize());
            file.setUserId(userService.getUser(principal.getName()).getUserId());
            file.setFileData(fileUpload.getBytes());

            int fileId = fileService.insert(file);

            if (fileId < 0) {
                errorMessage = "There was an error adding your file.";
            } else {
                model.addAttribute("success", true);
            }
        }

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "result";
    }
}
