package com.edu.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.edu.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class AliOssFileStorageServiceImpl implements FileStorageService {


    @Value("${edu.alioss.endpoint}")
    private String endpoint;

    @Value("${edu.alioss.access-key-id}")  // 匹配配置文件中的 access-key-id
    private String accessKeyId;

    @Value("${edu.alioss.access-key-secret}")  // 匹配配置文件中的 access-key-secret
    private String accessKeySecret;

    @Value("${edu.alioss.bucket-name}")  // 匹配配置文件中的 bucket-name
    private String bucketName;

    @Override
    public String upload(MultipartFile file, String filePath) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, filePath, file.getInputStream());
            return filePath;
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void download(String filePath, OutputStream outputStream) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = null;
        InputStream inputStream = null;
        try {
            // 1. 获取OSS对象
            ossObject = ossClient.getObject(new GetObjectRequest(bucketName, filePath));

            // 2. 获取输入流
            inputStream = ossObject.getObjectContent();

            // 3. 手动将输入流写入输出流
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } finally {
            // 4. 关闭资源（重要！）
            if (inputStream != null) {
                inputStream.close();
            }
            if (ossObject != null) {
                ossObject.close();
            }
            ossClient.shutdown();
        }
    }
}