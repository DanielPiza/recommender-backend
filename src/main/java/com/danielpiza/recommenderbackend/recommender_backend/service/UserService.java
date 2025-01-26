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

            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

            user.setCreatedDate(LocalDateTime.now());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }

    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    
            if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
                existingUser.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String hashedPassword = hashPassword(updatedUser.getPassword());
                existingUser.setPassword(hashedPassword);
            }
            updatedUser.setCreatedDate(existingUser.getCreatedDate());
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
        
    }

    public String deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return "User deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage());
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
