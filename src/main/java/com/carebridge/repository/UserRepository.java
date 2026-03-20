package com.carebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carebridge.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}