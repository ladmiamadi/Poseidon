package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Iterable<User> getUserList() {
        return userRepository.findAll();
    }

    public User createNewUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Integer id) {
        return userRepository.getById(id);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
