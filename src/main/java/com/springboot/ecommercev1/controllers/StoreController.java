package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.CategoryService;
import com.springboot.ecommercev1.services.ImageService;
import com.springboot.ecommercev1.services.ProductService;
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

    public StoreController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
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

}
