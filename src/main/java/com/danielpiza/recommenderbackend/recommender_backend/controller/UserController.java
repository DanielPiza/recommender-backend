package com.danielpiza.recommenderbackend.recommender_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.danielpiza.recommenderbackend.recommender_backend.model.User;
import com.danielpiza.recommenderbackend.recommender_backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping("/register")
    /*
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }*/
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully: " + savedUser.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}