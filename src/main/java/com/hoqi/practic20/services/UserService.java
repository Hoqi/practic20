package com.hoqi.practic20.services;

import com.hoqi.practic20.models.User;
import com.hoqi.practic20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User addUser(User user){
        return this.userRepository.findByEmail(user.getEmail())
                .orElse(this.userRepository.save(user));
    }

    public User getUser(Integer id){
        return this.userRepository.findById(id).get();
    }

    public User ChangeEmail(Integer Id,String newEmail){
        User targetUser = this.userRepository.findById(Id).get();
        targetUser.setEmail(newEmail);
        return this.userRepository.save(targetUser);
    }
}
