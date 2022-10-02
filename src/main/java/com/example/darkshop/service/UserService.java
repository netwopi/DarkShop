package com.example.darkshop.service;


import com.example.darkshop.model.User;
import com.example.darkshop.model.enums.Role;
import com.example.darkshop.repositori.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_ADMIN);
        log.info("Saving new User with email: {}", email);
        userRepository.save(user);
        return true;
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public void banUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        if (user.isActive()) {
            user.setActive(false);
            log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
        } else {
            user.setActive(true);
            log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
        }
        userRepository.save(user);
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void userDelete(Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        log.info("Delete user with id = {}; email: {}", user.getId(), user.getEmail());
    }

    public void userNameSubstitution(User user, Integer id) {
        User userFindId = userRepository.findById(id).orElseThrow();
        if (user.getName().length() != 0) {
            userFindId.setName(user.getName());
        } else {
            userFindId.setName(userFindId.getName());
        }
    }

    public void userEmailSubstitution(User user, Integer id) {
        User userFindId = userRepository.findById(id).orElseThrow();
        if (user.getEmail().length() != 0) {
            userFindId.setEmail(user.getEmail());
        } else {
            userFindId.setEmail(userFindId.getEmail());
        }
    }

    public void userPhoneNumberSubstitution(User user, Integer id) {
        User userFindId = userRepository.findById(id).orElseThrow();
        if (user.getPhoneNumber().length() != 0) {
            userFindId.setPhoneNumber(user.getPhoneNumber());
        } else {
            userFindId.setPhoneNumber(userFindId.getPhoneNumber());
        }
    }

    public void userPasswordSubstitution(User user, Integer id) {
        User userFindId = userRepository.findById(id).orElseThrow();
        if (user.getPassword().length() != 0) {
            userFindId.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            userFindId.setPassword(userFindId.getPassword());
        }
    }

    public void userUpdate(User user, Integer id) {
        User userFindId = userRepository.findById(id).orElseThrow();
        userNameSubstitution(user, id);
        userEmailSubstitution(user, id);
        userPhoneNumberSubstitution(user, id);
        userPasswordSubstitution(user, id);
        userFindId.setActive(true);
        userFindId.getRoles().add(Role.ROLE_ADMIN);
        userFindId.setDateOfCreated(LocalDateTime.now());
        log.info("Saving  User with email: {}", userFindId.getEmail());
        userRepository.save(userFindId);
    }
}
