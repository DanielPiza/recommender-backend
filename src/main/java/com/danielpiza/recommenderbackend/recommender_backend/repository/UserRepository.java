package com.danielpiza.recommenderbackend.recommender_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielpiza.recommenderbackend.recommender_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
