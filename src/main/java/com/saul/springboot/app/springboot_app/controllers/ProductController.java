package com.saul.springboot.app.springboot_app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.saul.springboot.app.springboot_app.ProductValidatior;
import com.saul.springboot.app.springboot_app.models.Product;
import com.saul.springboot.app.springboot_app.services.ProductServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductServiceImpl service;

    // private ProductValidatior validatior;

    @Autowired
    public void setService(ProductServiceImpl service) {
        this.service = service;
    }

    // @Autowired
    // public void setValidatior(ProductValidatior validatior) {
    //     this.validatior = validatior;
    // }


    @GetMapping
    public List<Product> findAll (){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity <Product> view(@PathVariable Long id){
        Optional<Product> productOpt = service.findById(id);
        if (productOpt.isPresent()) {
            return ResponseEntity.ok(productOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody Product product, BindingResult result){
        // validatior.validate(product, result);

        if (result.hasFieldErrors()) {
            return valid(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id){
        // validatior.validate(product, result);

        if (result.hasFieldErrors()) {
            return valid(result);
        }

        Optional <Product> productOptional = service.update(id, product);

        if (productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        Optional<Product> productOpt = service.delete(id);

        if (productOpt.isPresent()) {
            return ResponseEntity.ok(productOpt.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> valid(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errors.put(err.getField(), "El campo " + err.getField() +" es requerido " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
