package com.saul.springboot.app.springboot_app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saul.springboot.app.springboot_app.models.User;
import com.saul.springboot.app.springboot_app.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> viewAll(){
        return userService.findAll();
    }

    @PostMapping
    public ResponseEntity <?> save (@Valid @RequestBody User user, BindingResult result){
        if (result.hasFieldErrors()) {
            return valid(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    private ResponseEntity<?> valid(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "El campo " + err.getField() +" es requerido " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }



}
