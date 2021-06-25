package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFileNameAvailable(MultipartFile file, int userId) {
        boolean isFileNameAvailable = true;
        for(UserFile temp : getUserFiles(userId)) {
            if(temp.getFileName().equals(file.getOriginalFilename())) {
                isFileNameAvailable = false;
                break;
            }
        }
        return isFileNameAvailable;
    }

    public int insert(UserFile userFile) {
        return fileMapper.addFile(userFile);
    }

    public UserFile getFile(Integer id) {
        return fileMapper.getFile(id);
    }

    public List<UserFile> getUserFiles(Integer userId) {
        return fileMapper.getUserFiles(userId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

}
