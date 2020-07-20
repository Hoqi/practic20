package com.hoqi.practic20.services;

import com.hoqi.practic20.models.User;
import com.hoqi.practic20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean addUser(User user){
        if (!this.userRepository.findByEmail(user.getEmail()).isPresent()){
            this.userRepository.save(user);
            return true;
        }
         return false;
    }

    public User getUser(Integer id){
        return this.userRepository.findById(id).orElse(null);
    }

    public boolean ChangeEmail(Integer Id,String newEmail){
        Optional<User> targetUser = this.userRepository.findById(Id);
        if (targetUser.isPresent()) {
            User user = targetUser.get();
            user.setEmail(newEmail);
            userRepository.save(user);
        }
        return false;

    }
}
