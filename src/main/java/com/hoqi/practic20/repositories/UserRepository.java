package com.hoqi.practic20.repositories;

import com.hoqi.practic20.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
