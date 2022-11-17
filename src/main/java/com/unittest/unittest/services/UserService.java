package com.unittest.unittest.services;

import com.unittest.unittest.entities.User;
import com.unittest.unittest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        return userList;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
    }

    public void deleteAll() {
    }

    public void deleteById(Long id) {
    }
}
