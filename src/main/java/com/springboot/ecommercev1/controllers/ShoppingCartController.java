package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author KMCruz
 * 6/17/2021
 */
@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public String showShoppingCart(HttpSession session, Model model) {
        ShoppingCart shoppingCart = Optional.ofNullable(shoppingCartService.findById(session.getId())).orElse(new ShoppingCart());
        System.out.println(shoppingCart.getShoppingCartList().size());
        model.addAttribute("cartLineItems", shoppingCart.getShoppingCartList());
        return "store/shoppingCart";
    }
}
