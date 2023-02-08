package com.pulidodev.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pulidodev.security.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}