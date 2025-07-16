package com.edu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;

public interface FileStorageService {
    String upload(MultipartFile file, String filePath) throws IOException;
    void download(String filePath, OutputStream outputStream) throws IOException;
}