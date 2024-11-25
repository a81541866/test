package com.giggle.webflux.client.controller;

import com.giggle.webflux.api.api.TUserApi;
import com.giggle.webflux.api.dto.TUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;




@RestController
@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
@RequestMapping(value = "/demo")
public class TUserController {

    @Resource
    private TUserApi tUserApi;

    @GetMapping
    public Flux<TUserDto> findAll() {
        Flux<TUserDto> listFlux = tUserApi.findAll();
        log.info("查询用户列表线程返回");
        return listFlux;
    }

    @GetMapping (path = "{id}")
    public Mono<TUserDto> findById(@PathVariable("id") long id) {
        return tUserApi.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TUserDto> save(@RequestBody Mono<TUserDto> tUserDto) {
        return tUserApi.save(tUserDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update (@RequestBody Mono<TUserDto> tUserDto) {
        return tUserApi.update(tUserDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete (@RequestBody Mono<TUserDto> tUserDto) {
        return tUserApi.delete(tUserDto);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById (@PathVariable long id) {
        return tUserApi.deleteById(id);
    }
}
