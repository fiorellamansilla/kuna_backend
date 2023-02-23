package com.kuna_backend.services;

import com.kuna_backend.models.User;
import com.kuna_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
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
