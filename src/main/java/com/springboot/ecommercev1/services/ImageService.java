package com.springboot.ecommercev1.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author KMCruz
 * 6/10/2021
 */
public interface ImageService {
    void saveImageFile(Long productId, MultipartFile file);
}
