package com.saul.springboot.app.springboot_app;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.saul.springboot.app.springboot_app.models.Product;

@Component
public class ProductValidatior implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product)target;

        if (product.getDescription().isBlank() || product.getDescription() == null) {
            errors.rejectValue("description", null, "La descripcion no puede ser nula ni estar en blanco!");
        }

        if (product.getName() == null || product.getName() == null) {
            errors.rejectValue("name", null,"El nombre no puede ser nulo ni estar vacio!");
        }

        if (product.getPrice() == null) {
            errors.rejectValue("price", null, "El precio no puede ser nulo!");
        }else if (product.getPrice() < 20) {
            errors.rejectValue("price", null,"El precio no puede ser menor a 20!");
        }

    }

}
