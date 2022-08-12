package com.example.buysell.services;

import com.example.buysell.models.User;
import com.example.buysell.models.enums.Role;
import com.example.buysell.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LogManager.getLogger(ProductService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean createUser(User user){
        String userEmail = user.getEmail();
        if(userRepository.findByEmail(userEmail)!=null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving new User with email:{}",userEmail);
        userRepository.save(user);
        return true;
    }
}
