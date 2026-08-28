package com.saul.springboot.app.springboot_app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saul.springboot.app.springboot_app.models.Role;
import com.saul.springboot.app.springboot_app.models.User;
import com.saul.springboot.app.springboot_app.repositories.RoleRespository;
import com.saul.springboot.app.springboot_app.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRespository roleRespository;

    private final PasswordEncoder passwordEncoder;

    
    public UserServiceImpl(UserRepository userRepository, RoleRespository roleRespository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRespository = roleRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        List<Role> roles = new ArrayList<>();

        Optional<Role> optionalRoleUser = roleRespository.findByName("ROLE_USER");
        optionalRoleUser.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRespository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
