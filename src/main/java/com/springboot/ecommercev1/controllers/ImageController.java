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
@RequestMapping("/admin/categories/{categoryId}/products")
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;

    public ImageController(ImageService imageService, ProductService productService) {
        this.imageService = imageService;
        this.productService = productService;
    }

    @GetMapping("/{productId}/image")
    public String showUploadImageForm(@PathVariable Long productId, Model model){
        model.addAttribute("product", productService.findById(productId));
        return "images/uploadImage";
    }

    @PostMapping("/{productId}/image")
    public String processUploadImageForm(@PathVariable Long productId,@PathVariable Long categoryId, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(productId,file);
        String redirectUrl = "redirect:/categories/" +categoryId+"/products/" + productId;
        return redirectUrl;

    }

    @GetMapping("/{productId}/productImage")
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
