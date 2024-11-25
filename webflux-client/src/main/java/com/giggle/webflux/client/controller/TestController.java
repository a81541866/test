package com.giggle.webflux.client.controller;

import com.giggle.webflux.api.api.TestApi;
import com.giggle.webflux.server.common.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



@RestController
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestApi testApi;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final int maxReaders = 2; // 最多两个实例可以同时读取
    private final String filePath = "C:\\1.txt";


    @GetMapping
    public  Flux<String> sayHi() {
        lock.readLock().lock();
        try {
            if (lock.getReadLockCount() >= maxReaders) {
                throw new AppException(429,"已达到Ping最大请求数.......");
            }
            File file = new File(filePath);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            try (FileLock fileLock = fileChannel.lock()) {
                Flux<String> stringFlux = testApi.sayHi();
                fileLock.close();
                return stringFlux;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }

}
