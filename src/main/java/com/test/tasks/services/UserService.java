package com.test.tasks.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.tasks.entity.User;
import com.test.tasks.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User task) {
        return userRepository.save(task);
    }

    public User getUserById(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent())
            return existingUser.get();
        return null;
    }

    public User updateUser(Long userId, User updatedUser) {
        // Retrieve the existing task from the database.
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        // Check if the task exists in the database.
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Delete the task from the database.
        userRepository.delete(existingUser);
    }
}
