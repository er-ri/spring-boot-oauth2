package com.example.securitylogin.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.securitylogin.domain.MyUserDetails;
import com.example.securitylogin.domain.UserEntity;
import com.example.securitylogin.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName){
        UserEntity user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new MyUserDetails(user);
    }
    
    public void addUser(UserEntity user) {
    	userRepository.save(user);
    }
}