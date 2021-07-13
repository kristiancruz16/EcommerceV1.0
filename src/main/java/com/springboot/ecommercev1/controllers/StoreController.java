package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.*;
import com.springboot.ecommercev1.services.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Optional;
import java.util.function.Consumer;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author KMCruz
 * 6/13/2021
 */

@Controller
@RequestMapping
public class StoreController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ShoppingCartLineItemService shoppingCartLineItemService;
    private final ShoppingCartService shoppingCartService;
    private final CustomerService customerService;


    public StoreController(CategoryService categoryService, ProductService productService,
                           ShoppingCartLineItemService shoppingCartLineItemService,
                           ShoppingCartService shoppingCartService, CustomerService customerService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.shoppingCartLineItemService = shoppingCartLineItemService;
        this.shoppingCartService = shoppingCartService;
        this.customerService = customerService;
    }

    @GetMapping("")
    public String displayHomePage (Model model, Principal principal) {
        String cartQuantity = getCartQuantity(principal);
        model.addAttribute("products",productService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("cart",cartQuantity);
        return "store/homePage";
    }

    @GetMapping("/categories")
    public String filerProductsByCategory (Principal principal, @RequestParam String categoryName, Model model) {
        String cartQuantity = getCartQuantity(principal);
        model.addAttribute("category",categoryService.findCategoryByName(categoryName));
        model.addAttribute("cart",cartQuantity);
        return "store/categoryDetails";
    }

    @GetMapping("/categories/products/image")
    public void renderImageFromDatabase(@RequestParam Long sku, HttpServletResponse response) throws IOException {

        Product product = productService.findProductBySku(sku);

        if(product.getImage()!=null) {
            Byte [] imageByteObject = product.getImage();

            byte [] imageBytePrimitive = new byte[imageByteObject.length];
            int i=0;
            for(Byte productImageByte : imageByteObject) {
                imageBytePrimitive[i++] = productImageByte;
            }
            response.setContentType("image/jpg");
            InputStream inputStream = new ByteArrayInputStream(imageBytePrimitive);
            IOUtils.copy(inputStream,response.getOutputStream());

        }
    }
    @GetMapping("/categories/products")
    public String showProductDetails(Principal principal,@RequestParam Long sku, Model model) {
        Product product = productService.findProductBySku(sku);
        String cartQuantity = getCartQuantity(principal);
        model.addAttribute("product",product);
        model.addAttribute("cart",cartQuantity);
        return "store/productDetails";
    }
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping("/categories/products")
    public String addToCart(@RequestParam Long sku, Principal principal) {
        Product product = productService.findProductBySku(sku);
        Customer customer = customerService.findLoggedInCustomer(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
//        ShoppingCart savedCart = shoppingCartService.save(cartToSave);

        ShoppingCartLineItemKey compositePrimaryKey = ShoppingCartLineItemKey.builder()
                .productId(product.getId())
                .shoppingCartId(shoppingCart.getId())
                .build();

        ShoppingCartLineItem cartLineItem = ShoppingCartLineItem.builder()
                .id(compositePrimaryKey)
                .shoppingCart(shoppingCart)
                .product(product)
                .build();
        ShoppingCartLineItem cartLineItemToSave = cartLineItem.addCartLineItem();

        shoppingCartLineItemService.save(cartLineItemToSave);

        return "redirect:/categories/products?sku="+product.getSku();
    }

    private String getCartQuantity(Principal principal) {
        String cartQuantity;
        try {
            Customer customer = customerService.findLoggedInCustomer(principal.getName());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            cartQuantity = shoppingCartLineItemService.totalQuantityByShoppingCartID(shoppingCart.getId());
        } catch (NullPointerException nullEx) {
            cartQuantity = "";
        }
        return cartQuantity;
    }

}
