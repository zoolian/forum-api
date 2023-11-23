package com.jmscottnovels.forumapi.service;

import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.jmscottnovels.forumapi.exception.UserAlreadyExistException;
import com.jmscottnovels.forumapi.model.User;
import com.jmscottnovels.forumapi.repo.RoleRepository;
import com.jmscottnovels.forumapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(final User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistException("There is an account with that email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }
}
