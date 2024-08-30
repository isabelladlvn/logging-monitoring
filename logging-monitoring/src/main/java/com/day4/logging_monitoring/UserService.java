package com.day4.logging_monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> searchedUser = userRepository.findById(id);
        return searchedUser.orElse(null);
    }

    public void updateUser(Long id, User book) {
        if (getUserById(id) != null) {
            userRepository.deleteById(id);
            userRepository.save(book);
        }
    }

}
