package com.springboot.ecommercev1.services;

import com.springboot.ecommercev1.domain.Product;
import com.springboot.ecommercev1.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author KMCruz
 * 6/10/2021
 */
@Service
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;

    public ImageServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveImageFile(Long productId, MultipartFile file) {
        try {

            Product product =productRepository.findById(productId).get();

            byte [] byteArray = file.getBytes();

            int byteArraySize = byteArray.length;

            Byte [] byteObjects =new Byte[byteArraySize];

            int i = 0;

            for(byte b : byteArray) {
                byteObjects[i++] = b;
            }

            product.setImage(byteObjects);

            productRepository.save(product);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
