package com.example.securitylogin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitylogin.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserName(String userName);
    
}

