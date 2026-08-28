package com.saul.springboot.app.springboot_app.services;

import java.util.List;

import com.saul.springboot.app.springboot_app.models.User;

public interface UserService {

    List<User> findAll ();

    User save (User user);

}
