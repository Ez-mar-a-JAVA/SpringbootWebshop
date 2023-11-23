package com.EzmarJava.Webshop.service.impl;

import com.EzmarJava.Webshop.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService
{
    @Override
    public void store(MultipartFile file, String destination)
    {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(destination + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
