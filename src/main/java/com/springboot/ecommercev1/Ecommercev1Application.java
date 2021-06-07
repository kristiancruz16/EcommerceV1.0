package com.springboot.ecommercev1;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.CategoryRepository;
import com.springboot.ecommercev1.repositories.ProductRepository;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Ecommercev1Application {

    public static void main(String[] args) {

        SpringApplication.run(Ecommercev1Application.class, args);
    }

}


