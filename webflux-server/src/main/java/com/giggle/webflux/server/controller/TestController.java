package com.giggle.webflux.server.controller;

import com.giggle.webflux.api.dto.TUserDto;
import com.giggle.webflux.server.convert.TUserConvert;
import com.giggle.webflux.server.interceptor.Limiting;
import com.giggle.webflux.server.service.TUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/world")
public class TestController {


    @GetMapping
    @Limiting(limitNum = 1, name = "World")
    public Flux<String> World() {
        String reMsg = "WORLD";
        log.info("查询用户列表线程返回");
        return Flux.just(reMsg);
    }

}
