package com.saul.springboot.app.springboot_app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saul.springboot.app.springboot_app.models.Role;


public interface RoleRespository extends CrudRepository<Role, Long>{

    Optional <Role> findByName(String name);

}
