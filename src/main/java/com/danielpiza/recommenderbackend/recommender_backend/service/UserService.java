package com.danielpiza.recommenderbackend.recommender_backend.service;

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
}