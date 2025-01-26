package com.danielpiza.recommenderbackend.recommender_backend.service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielpiza.recommenderbackend.recommender_backend.model.User;
import com.danielpiza.recommenderbackend.recommender_backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new Error("Email can't be empty");
            }
            if (user.getPassword().isEmpty()) {
                throw new Error("Password can't be empty");
            }

            // Generar hash de la contraseña con SHA-256
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            
            user.setCreatedDate(LocalDateTime.now());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }

    }

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

     // Método para realizar el hash de la contraseña
     private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage());
        }
    }

    // Conversión de bytes a String hexadecimal
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
