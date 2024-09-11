package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.User;
import io.mountblue.blog_application_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUserDetails(String email)
    {
        return  userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkValidity(String email) {
        User user=getUserDetails(email);
        return user!=null;
    }

    public void save(User user) {
        userRepository.save(user);
    }
}