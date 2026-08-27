package com.saul.springboot.app.springboot_app.services;

import java.util.List;
import java.util.Optional;


import com.saul.springboot.app.springboot_app.models.Product;


public interface ProductService {

    List<Product> findAll ();

    Optional<Product> findById (Long id);

    Product save (Product product);

    Optional<Product> update (Long id, Product product);

    Optional<Product> delete (Long id);

}
