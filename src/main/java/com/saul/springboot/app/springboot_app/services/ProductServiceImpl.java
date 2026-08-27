package com.saul.springboot.app.springboot_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saul.springboot.app.springboot_app.models.Product;
import com.saul.springboot.app.springboot_app.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {
        Optional <Product> productOpt = repository.findById(id);

        if (productOpt.isPresent()) {
            Product productDb = productOpt.orElseThrow();
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(repository.save(productDb));
        }
        return productOpt;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> productOpt = repository.findById(id);
        productOpt.ifPresent(productDb ->{
            repository.delete(productDb);
        });
        return productOpt;
    }


}
