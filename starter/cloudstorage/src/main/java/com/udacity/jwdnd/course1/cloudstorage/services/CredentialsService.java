package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.data.UserCredentials;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentialsForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public UserCredentials getCredentials(Integer credentialId) {
        UserCredentials credentials = credentialsMapper.getCredentials(credentialId);
        credentials.setDecryptedPassword(encryptionService.decryptValue(credentials.getPassword(), credentials.getKey()));
        return credentials;
    }

    public List<UserCredentials> getUserCredentials(Integer userId) {
        List<UserCredentials> userCredentials = credentialsMapper.getUserCredentials(userId);
        for (UserCredentials credentials : userCredentials) {
            credentials.setDecryptedPassword(encryptionService.decryptValue(credentials.getPassword(), credentials.getKey()));
        }
        return userCredentials;
    }

    public String addCredentials(UserCredentialsForm userCredentialsForm, Integer userId, boolean update) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(userCredentialsForm.getCredentialPassword(), encodedKey);

        UserCredentials credentials = new UserCredentials(
                userCredentialsForm.getCredentialId(),
                userCredentialsForm.getCredentialUrl(),
                userCredentialsForm.getCredentialUsername(),
                encodedKey,
                encryptedPassword,
                userId
        );

        Integer result;
        if (update) {
            result = credentialsMapper.updateCredentials(credentials);
        } else {
            result = credentialsMapper.addCredentials(credentials);
        }

        if (result < 0) {
            return "There was an error adding your credentials. Please retry.";
        } else {
            return null;
        }
    }

    public void deleteCredentials(Integer credentialId) {
        credentialsMapper.deleteCredentials(credentialId);
    }
}
