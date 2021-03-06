package com.springboot.ecommercev1.bootstrap;

import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import com.springboot.security.models.Role;
import com.springboot.security.models.User;
import com.springboot.security.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author KMCruz
 * 6/6/2021
 */
@Component
public class DataLoader implements CommandLineRunner {

        private final ProductService productService;
        private final CategoryService categoryService;
        private final ShoppingCartService shoppingCartService;
        private final ShoppingCartLineItemService shoppingCartLineItemService;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;

        public DataLoader(ProductService productService, CategoryService categoryService, ShoppingCartService shoppingCartService, ShoppingCartLineItemService shoppingCartLineItemService, PasswordEncoder passwordEncoder, UserService userService) {
            this.productService = productService;
            this.categoryService = categoryService;
            this.shoppingCartService = shoppingCartService;
            this.shoppingCartLineItemService = shoppingCartLineItemService;
            this.passwordEncoder = passwordEncoder;
            this.userService = userService;
        }

        @Override
        public void run(String... args) throws Exception {

            Category mensBasketballShoes = new Category();
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
            ladiesRunningShoes.setName("Ladies' Running Shoes");
            Category savedLadiesRunningShoes = categoryService.save(ladiesRunningShoes);

            Product rebookRunningShoes = new Product();
            rebookRunningShoes.setName("Reebok Running Shoes");
            rebookRunningShoes.setSku(40198765L);
            rebookRunningShoes.setProductDescription("Pink/US 7.0");
            rebookRunningShoes.setProductPrice(2500.00);
            rebookRunningShoes.setCategory(savedLadiesRunningShoes);
            ladiesRunningShoes.getProducts().add(rebookRunningShoes);
            Product savedRebookRunningShoes = productService.save(rebookRunningShoes);

            ShoppingCart shoppingCart = new ShoppingCart();
            ShoppingCart savedShoppingCart = shoppingCartService.save(shoppingCart);


            ShoppingCartLineItemKey shoppingCartLineItemKey = new ShoppingCartLineItemKey();
            shoppingCartLineItemKey.setProductId(savedRebookRunningShoes.getId());
            shoppingCartLineItemKey.setShoppingCartId(savedShoppingCart.getId());

            ShoppingCartLineItem shoppingCartLineItem = new ShoppingCartLineItem();

            shoppingCartLineItem.setId(shoppingCartLineItemKey);
            shoppingCartLineItem.setProduct(savedRebookRunningShoes);
            shoppingCartLineItem.setShoppingCart(savedShoppingCart);
            shoppingCartLineItem.setQuantity(1);
            shoppingCartLineItem.setLineAmount(shoppingCartLineItem.getQuantity()
                    *savedRebookRunningShoes.getProductPrice());

            shoppingCartLineItemService.save(shoppingCartLineItem);

            User userAdmin = new User();
            userAdmin.setFirstName("Admin");
            userAdmin.setLastName("Account");
            userAdmin.setEmail("tayeah12@gmail.com");
            userAdmin.setPassword(passwordEncoder.encode("!Mar0604"));
            userAdmin.setRole(Role.ADMIN);
            userAdmin.setEnabled(true);
            userService.savedRegisteredUser(userAdmin);

            User userTest = new User();
            userTest.setFirstName("Test");
            userTest.setLastName("User");
            userTest.setEmail("usertest@email.com");
            userTest.setPassword(passwordEncoder.encode("!Mar0604"));
            userTest.setRole(Role.USER);
            userTest.setEnabled(true);
            userService.savedRegisteredUser(userTest);
        }
}
