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
        String originalFileName = file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(destination + originalFileName);

            // Check if file already exists
            if(Files.exists(path))
            {
                String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                String newFileName = fileNameWithoutExtension + "_" + System.currentTimeMillis() + fileExtension;
                path = Paths.get(destination, newFileName);
            }

            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
