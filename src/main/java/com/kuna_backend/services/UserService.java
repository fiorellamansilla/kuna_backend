package com.kuna_backend.services;

import com.kuna_backend.entities.User;
import com.kuna_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser (Integer id) {
        return userRepository.findById(id).get();
    }

    public void createUser (User user) {
        userRepository.save(user);
    }

    public void deleteUser (Integer id) {
        userRepository.deleteById(id);
    }

}
