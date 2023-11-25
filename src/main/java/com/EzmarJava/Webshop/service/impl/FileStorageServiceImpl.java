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
    public String store(MultipartFile file, String destination)
    {
        String fileName = file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(destination + fileName);

            // Check if file already exists
            if(Files.exists(path))
            {
                String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                fileName = fileNameWithoutExtension + "_" + System.currentTimeMillis() + fileExtension;
                path = Paths.get(destination, fileName);
            }

            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
