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
        String cartQuantity = shoppingCartLineItemService.totalQuantityByShoppingCartID(session.getId());
        model.addAttribute("products",productService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("cart",cartQuantity);
        return "store/homePage";
    }

    @GetMapping("/{categoryId}/products/{productId}")
    public String showProductDetails(HttpSession session,@PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        String cartQuantity = shoppingCartLineItemService.totalQuantityByShoppingCartID(session.getId());

        model.addAttribute("product",product);
        model.addAttribute("cart",cartQuantity);
        return "store/productDetails";
    }

    @GetMapping("/{categoryId}")
    public String filerProductsByCategory (HttpSession session, @PathVariable Long categoryId, Model model) {
        String cartQuantity = shoppingCartLineItemService.totalQuantityByShoppingCartID(session.getId());
        model.addAttribute("category",categoryService.findById(categoryId));
        model.addAttribute("cart",cartQuantity);
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
    public String addToCart(HttpSession session, @PathVariable Long productId, @PathVariable Long categoryId) {
        Product product = productService.findById(productId);
        ShoppingCart cartToSave = new ShoppingCart();
        cartToSave.setId(session.getId());
        ShoppingCart savedCart = shoppingCartService.save(cartToSave);

        ShoppingCartLineItemKey compositePrimaryKey = ShoppingCartLineItemKey.builder()
                .productId(product.getId()).shoppingCartId(savedCart.getId()).build();

        ShoppingCartLineItem cartLineItem = ShoppingCartLineItem.builder()
                .id(compositePrimaryKey).shoppingCart(savedCart)
                .product(product).build();

        shoppingCartLineItemService.save(cartLineItem);

        return "redirect:/" + categoryId + "/products/" + productId;
    }

}
