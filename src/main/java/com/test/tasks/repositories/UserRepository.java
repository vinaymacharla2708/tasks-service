package com.test.tasks.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.tasks.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
}