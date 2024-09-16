package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.User;
import io.mountblue.blog_application_project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserDetails(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkValidity(String email) {
        User user = getUserDetails(email);
        return user != null;
    }

    public void save(User user) {
        userRepository.save(user);
    }
}