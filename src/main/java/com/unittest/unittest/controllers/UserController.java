package com.unittest.unittest.controllers;

import com.unittest.unittest.entities.User;
import com.unittest.unittest.repositories.UserRepository;
import com.unittest.unittest.services.UserService;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAllUsers(){
        List<User> user = userService.findAll();
        return user;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        return user;
    }

    @PostMapping
    public void createUser(@RequestBody User user){
       userService.save(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id,@RequestBody User user){
        user.setId(id);
        userService.save(user);
    }

    @DeleteMapping
    public void deleteAllUsers(){
        userService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteSingleUser(@PathVariable Long id){
        userService.deleteById(id);
    }
}
