package com.hoqi.practic20.services;

import com.hoqi.practic20.exceptions.NotFoundException;
import com.hoqi.practic20.exceptions.UserExistException;
import com.hoqi.practic20.models.User;
import com.hoqi.practic20.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) throws UserExistException {
        if (this.userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserExistException("Пользователь с данным именем уже существует");
        }
        this.userRepository.save(user);
        return user;
    }

    public User getUser(String email) throws NotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public User ChangeEmail(Integer Id, String newEmail) throws NotFoundException {
        Optional<User> targetUser = this.userRepository.findById(Id);
        if (!targetUser.isPresent()) {
            throw new NotFoundException("Пользователь с данным id не найден");
        }
        User user = targetUser.get();
        user.setEmail(newEmail);
        userRepository.save(user);
        return user;
    }
}
