package com.EzmarJava.Webshop.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService
{
    void store(MultipartFile file, String destination);
}
