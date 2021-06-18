package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * @author KMCruz
 * 6/17/2021
 */
@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final ShoppingCartLineItemService shoppingCartLineItemService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService,
                                  ShoppingCartLineItemService shoppingCartLineItemService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.shoppingCartLineItemService = shoppingCartLineItemService;
    }

    @GetMapping
    public String showShoppingCart(HttpSession session, Model model) {
        ShoppingCart shoppingCart = Optional.ofNullable(shoppingCartService.findById(session.getId())).orElse(new ShoppingCart());
        System.out.println(shoppingCart.getShoppingCartList().size());
        model.addAttribute("cartLineItems", shoppingCart.getShoppingCartList());
        return "store/shoppingCart";
    }


}
