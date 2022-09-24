package com.example.darkshop.services;


import com.example.darkshop.models.Image;
import com.example.darkshop.models.User;
import com.example.darkshop.models.enums.Role;
import com.example.darkshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; email: {}", user.getId(), user.getEmail());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; email: {}", user.getId(), user.getEmail());
            }
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

    public void userDelete(Long id) {
        User user = userRepository.findById(id).orElseThrow(null);
        if (user != null) {
            userRepository.delete(user);
            log.info("Delete user with id = {}; email: {}", user.getId(), user.getEmail());
        }else {
            userRepository.delete(null);
            log.info("Delete user with id = {}; email: {}", user.getId(), user.getEmail());
        }
    }
    public void updateUser(User user , Long id , MultipartFile file4) throws IOException {
        Image image4;
        User userFindId = userRepository.findById(id).orElseThrow(null);
        if (user.getName().length() != 0){
            userFindId.setName(user.getName());
        }else {
            userFindId.setName(userFindId.getName());
        }

        if (user.getEmail().length() != 0){
            userFindId.setEmail(user.getEmail());
        }else {
            userFindId.setEmail(userFindId.getEmail());
        }

        if (user.getPhoneNumber().length() != 0){
            userFindId.setPhoneNumber(user.getPhoneNumber());
        }else {
            userFindId.setPhoneNumber(userFindId.getPhoneNumber());
        }

        if (user.getPassword().length() != 0){
            userFindId.setPassword(passwordEncoder.encode(user.getPassword()));
        }else {
            userFindId.setPassword(userFindId.getPassword());
        }
/*           image4 = toImageEntity(file4);
            userFindId.addImageToUser(image4);
            System.out.println("2-");*/

        userFindId.setActive(true);
        userFindId.getRoles().add(Role.ROLE_ADMIN);
        userFindId.setDateOfCreated(LocalDateTime.now());
        log.info("Saving  User with email: {}", userFindId.getEmail());
        userRepository.save(userFindId);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image imageAvatar = new Image();
        imageAvatar.setName(file.getName());
        imageAvatar.setOriginalFileName(file.getOriginalFilename());
        imageAvatar.setContentType(file.getContentType());
        imageAvatar.setSize(file.getSize());
        imageAvatar.setBytes(file.getBytes());
        return imageAvatar;
    }

}
