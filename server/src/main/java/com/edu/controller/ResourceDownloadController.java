package com.edu.controller;

import com.edu.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/resources")
public class ResourceDownloadController {

    @Autowired
    private LearningResourceService resourceService;

    @GetMapping(value = "/{resourceId}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<Flux<DataBuffer>>> downloadResource(
            @PathVariable Long resourceId,
            ServerWebExchange exchange) {

        return Mono.fromCallable(() -> {
            // 创建内存输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 将资源写入输出流
            resourceService.downloadResource(resourceId, baos);

            // 转换为输入流
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

            // 创建响应数据
            Flux<DataBuffer> body = DataBufferUtils.readInputStream(
                    () -> bais,
                    exchange.getResponse().bufferFactory(),
                    4096
            );

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData(
                    "attachment",
                    URLEncoder.encode("resource_" + resourceId + ".dat", StandardCharsets.UTF_8)
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(baos.size())
                    .body(body);
        });
    }
}