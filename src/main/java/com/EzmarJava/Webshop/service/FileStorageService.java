package com.EzmarJava.Webshop.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService
{
    String store(MultipartFile file, String destination);
}
