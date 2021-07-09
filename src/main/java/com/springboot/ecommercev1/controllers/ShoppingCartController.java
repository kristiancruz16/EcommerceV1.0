package com.springboot.ecommercev1.controllers;


import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final ShoppingCartLineItemService shoppingCartLineItemService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  ShoppingCartLineItemService shoppingCartLineItemService) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartLineItemService = shoppingCartLineItemService;
    }

    @GetMapping
    public String showShoppingCart(HttpSession session, Model model) {
        ShoppingCart shoppingCart = Optional.ofNullable(shoppingCartService.findById(session.getId()))
                                            .orElse(new ShoppingCart());
        model.addAttribute("cartLineItems", shoppingCart.getShoppingCartList());
        return "store/shoppingCart";
    }


    @PostMapping("/add")
    public String addLineItemQuantity(@RequestParam String cartId, @RequestParam Long productId){

        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(cartId).productId(productId).build();
        ShoppingCartLineItem cartLineItem = shoppingCartLineItemService.findByID(key);
        ShoppingCartLineItem cartLineItemToSave = cartLineItem.addCartLineItem();
        shoppingCartLineItemService.save(cartLineItemToSave);
        return "redirect:/shoppingcart";
    }

    @PostMapping("/delete")
    public String deleteLineItemQuantity(@RequestParam String cartId, @RequestParam Long productId){
        ShoppingCartLineItemKey key = ShoppingCartLineItemKey.builder()
                .shoppingCartId(cartId).productId(productId).build();
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
    public String deleteAllLineItems (HttpSession session) {
        ShoppingCart shoppingCart = shoppingCartService.findById(session.getId());
        shoppingCart.getShoppingCartList().stream()
                .forEach(shoppingCartLineItemService::delete);
        return "redirect:/shoppingcart";
    }

}
