package com.springboot.ecommercev1.bootstrap;

import com.springboot.ecommercev1.domain.Category;
import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author KMCruz
 * 6/6/2021
 */
@Component
public class DataLoader implements CommandLineRunner {

        private final ProductService productService;
        private final CategoryService categoryService;

        public DataLoader(ProductService productService, CategoryService categoryService) {
            this.productService = productService;
            this.categoryService = categoryService;
        }

        @Override
        public void run(String... args) throws Exception {

            Category mensBasketballShoes = new Category();
            mensBasketballShoes.setCategoryCode("MBS");
            mensBasketballShoes.setName("Men's Basketball Shoes");
            Category savedMensBasketballShoes = categoryService.save(mensBasketballShoes);


            Product nikeBasketball = new Product();
            nikeBasketball.setName("Nike Basketball Shoes");
            nikeBasketball.setSku(10111235L);
            nikeBasketball.setProductDescription("Black/US 8.5");
            nikeBasketball.setProductPrice(4500.00);
            nikeBasketball.setCategory(savedMensBasketballShoes);
            mensBasketballShoes.getProducts().add(nikeBasketball);
            productService.save(nikeBasketball);

            Product adidasBasketball = new Product();
            adidasBasketball.setName("Adidas Basketball Shoes");
            adidasBasketball.setSku(20198765L);
            adidasBasketball.setProductDescription("White/US 11");
            adidasBasketball.setProductPrice(7000.00);
            adidasBasketball.setCategory(savedMensBasketballShoes);
            mensBasketballShoes.getProducts().add(adidasBasketball);
            productService.save(adidasBasketball);


            Category mensRunningShoes = new Category();
            mensRunningShoes.setCategoryCode("MRS");
            mensRunningShoes.setName("Men's Running Shoes");
            Category savedMensRunningShoes = categoryService.save(mensRunningShoes);

            Product nikeRunning = new Product();
            nikeRunning.setName("Nike Running Shoes");
            nikeRunning.setProductDescription("Gray/US 13.5");
            nikeRunning.setSku(12111235L);
            nikeRunning.setProductPrice(3500.00);
            nikeRunning.setCategory(savedMensRunningShoes);
            mensRunningShoes.getProducts().add(nikeRunning);
            productService.save(nikeRunning);

            Product underArmourRunning = new Product();
            underArmourRunning.setName("UnderArmour Running Shoes");
            underArmourRunning.setSku(30198765L);
            underArmourRunning.setProductDescription("Blue/US 9.5");
            underArmourRunning.setProductPrice(5500.00);
            underArmourRunning.setCategory(savedMensRunningShoes);
            mensRunningShoes.getProducts().add(underArmourRunning);
            productService.save(underArmourRunning);

            Category ladiesRunningShoes = new Category();
            ladiesRunningShoes.setCategoryCode("LRS");
            ladiesRunningShoes.setName("Ladies' Running Shoes");
            Category savedLadiesRunningShoes = categoryService.save(ladiesRunningShoes);

            Product rebookRunningShoes = new Product();
            rebookRunningShoes.setName("Reebok Running Shoes");
            rebookRunningShoes.setSku(40198765L);
            rebookRunningShoes.setProductDescription("Pink/US 7.0");
            rebookRunningShoes.setProductPrice(2500.00);
            rebookRunningShoes.setCategory(savedLadiesRunningShoes);
            ladiesRunningShoes.getProducts().add(rebookRunningShoes);
            productService.save(rebookRunningShoes);

        }
}
