package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Model model,
            Authentication authentication
    ) throws IOException {

        String username = authentication.getName();
        User user = userService.getUser(username);
        int userId = user.getUserId();

        String errorMessage = null;

        if (fileUpload.isEmpty()) {
            errorMessage = "Please select a file that is not empty.";
        } else {
            if (!fileService.isFileNameAvailable(fileUpload, userId)) {
                errorMessage = "Your selected file is already stored in the database.";
            } else {
                int fileId = fileService.insert(new UserFile(
                        0,
                        fileUpload.getOriginalFilename(),
                        fileUpload.getContentType(),
                        fileUpload.getSize(),
                        userId,
                        fileUpload.getBytes()
                ));

                if (fileId < 0) {
                    errorMessage = "There was an error adding your file.";
                } else {
                    model.addAttribute("success", true);
                }
            }
        }

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "result";
    }

    @GetMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable(value = "fileId") Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/file-download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileId") Integer fileId, Model model) {
        UserFile file = fileService.getFile(fileId);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFileName());
        header.add("Expires", "0");
        header.add("Cache-control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        ByteArrayResource resource = new ByteArrayResource((file.getFileData()));
        return ResponseEntity.ok()
                .headers(header)
                .body(resource);
    }



}
