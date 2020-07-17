package com.hoqi.practic20.controllers;

import com.hoqi.practic20.models.User;
import com.hoqi.practic20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public void insert(@RequestBody User user){
        userRepository.save(user);
    }
    @GetMapping
    public Iterable<User> list(){
        return userRepository.findAll();
    }
}
