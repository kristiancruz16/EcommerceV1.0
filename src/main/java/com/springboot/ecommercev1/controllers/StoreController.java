package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.domain.ShoppingCart;
import com.springboot.ecommercev1.domain.ShoppingCartLineItem;
import com.springboot.ecommercev1.domain.ShoppingCartLineItemKey;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ProductService;
import com.springboot.ecommercev1.services.ShoppingCartLineItemService;
import com.springboot.ecommercev1.services.ShoppingCartService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author KMCruz
 * 6/13/2021
 */

@Controller
@RequestMapping
public class StoreController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ShoppingCartLineItemService shoppingCartLineItemService;
    private final ShoppingCartService shoppingCartService;

    public StoreController(CategoryService categoryService, ProductService productService,
                           ShoppingCartLineItemService shoppingCartLineItemService,
                           ShoppingCartService shoppingCartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.shoppingCartLineItemService = shoppingCartLineItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping({""})
    public String displayHomePage (Model model, HttpSession session) {
        System.out.println(session.getId());
        model.addAttribute("products",productService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        return "store/homePage";
    }

    @GetMapping("/{categoryId}/products/{productId}")
    public String showProductDetails(@PathVariable Long productId, Model model) {
        model.addAttribute("product",productService.findById(productId));
        return "store/productDetails";
    }

    @GetMapping("/{categoryId}")
    public String filerProductsByCategory (@PathVariable Long categoryId, Model model) {
        model.addAttribute("category",categoryService.findById(categoryId));
        return "store/categoryDetails";
    }

    @GetMapping("/{categoryId}/products/{productId}/image")
    public void renderImageFromDatabase(@PathVariable Long productId, HttpServletResponse response) throws IOException {

        Product product = productService.findById(productId);

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

    @PostMapping("/{categoryId}/products/{productId}")
    public String addToCart(HttpSession session, @PathVariable Long productId) {
        Product product = productService.findById(productId);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(session.getId());
        shoppingCartService.save(shoppingCart);

        ShoppingCartLineItemKey shoppingCartLineItemKey = ShoppingCartLineItemKey.builder()
                .shoppingCartId(shoppingCart.getId())
                .productId(product.getId())
                .build();

        ShoppingCartLineItem shoppingCartLineItem = ShoppingCartLineItem.builder()
                .id(shoppingCartLineItemKey)
                .product(product)
                .shoppingCart(shoppingCart).build();
        shoppingCartLineItem.setQuantity(1);
        shoppingCartLineItem.setLineAmount(shoppingCartLineItem.getQuantity()*product.getProductPrice());

        shoppingCartLineItemService.save(shoppingCartLineItem);
        return "redirect:/" + product.getCategory().getId() + "/products/" +product.getId();
    }
}
