package com.saul.springboot.app.springboot_app.validatios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saul.springboot.app.springboot_app.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsSkuExistsDbValidatior implements ConstraintValidator<IsSkuExistsDB, String>{

    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }


   @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (service == null) {
            return true;
        }
        return !service.existsBySku(value);
    }

}
