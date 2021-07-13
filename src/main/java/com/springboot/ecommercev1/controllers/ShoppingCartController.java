package com.springboot.ecommercev1.controllers;


import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.CustomerService;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author KMCruz
 * 6/17/2021
 */
@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartLineItemService shoppingCartLineItemService;
    private final CustomerService customerService;
    private final ProductService productService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  ShoppingCartLineItemService shoppingCartLineItemService, CustomerService customerService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartLineItemService = shoppingCartLineItemService;
        this.customerService = customerService;
        this.productService = productService;
    }



    @GetMapping
    public String showShoppingCart(Model model, Principal principal) {
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("cartLineItems", shoppingCart.getShoppingCartList());
        return "store/shoppingCart";
    }


    @PostMapping("/add")
    public String addLineItemQuantity(@RequestParam Long productId, Principal principal){
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(shoppingCart.getId()).productId(productId).build();
        ShoppingCartLineItem cartLineItem = shoppingCartLineItemService.findByID(key);
        ShoppingCartLineItem cartLineItemToSave = cartLineItem.addCartLineItem();
        shoppingCartLineItemService.save(cartLineItemToSave);
        return "redirect:/shoppingcart";
    }

    @PostMapping("/delete")
    public String deleteLineItemQuantity(@RequestParam Long productId, Principal principal){
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(shoppingCart.getId()).productId(productId).build();
        ShoppingCartLineItem cartLineItem = shoppingCartLineItemService.findByID(key);
        Integer lineItemQuantity = cartLineItem.getQuantity() - 1;
        cartLineItem.setQuantity(lineItemQuantity);

        if (lineItemQuantity==0)
            shoppingCartLineItemService.delete(cartLineItem);
        else
            shoppingCartLineItemService.save(cartLineItem);

        return "redirect:/shoppingcart";
    }

    @PostMapping("/deleteAll")
    public String deleteAllLineItems (Principal principal) {
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        shoppingCart.getShoppingCartList().stream()
                .forEach(shoppingCartLineItemService::delete);
        return "redirect:/shoppingcart";
    }

}
