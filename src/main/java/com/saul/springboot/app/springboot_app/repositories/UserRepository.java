package com.saul.springboot.app.springboot_app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.saul.springboot.app.springboot_app.models.User;

public interface UserRepository extends CrudRepository<User, Long>{

    
} 

