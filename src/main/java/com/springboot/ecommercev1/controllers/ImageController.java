package com.springboot.ecommercev1.controllers;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.services.ImageService;
import com.springboot.ecommercev1.services.ProductService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author KMCruz
 * 6/10/2021
 */
@Controller
@RequestMapping("/admin/categories/products")
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;

    public ImageController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    @GetMapping("/image")
    public String showUploadImageForm(@RequestParam Long sku, Model model){
        model.addAttribute("product", productService.findProductBySku(sku));
        return "images/uploadImage";
    }

    @PostMapping("/image")
    public String processUploadImageForm(@RequestParam Long sku, @RequestParam("imagefile")MultipartFile file) {
        Product product = productService.findProductBySku(sku);
        imageService.saveImageFile(product.getId(),file);
        return "redirect:/admin/categories/products?sku="+product.getSku();
    }

    @GetMapping("/productImage")
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
}
