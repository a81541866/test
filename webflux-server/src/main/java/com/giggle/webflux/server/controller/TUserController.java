package com.giggle.webflux.server.controller;

import com.giggle.webflux.api.dto.TUserDto;
import com.giggle.webflux.server.convert.TUserConvert;
import com.giggle.webflux.server.service.TUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: guozichen
 * @Date: 2020/8/10 下午4:43
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/user")
public class TUserController {

    @Autowired
    private TUserService tUserService;

    @GetMapping
    public Flux<TUserDto> findAll() {
        Flux<TUserDto> listFlux = tUserService.findAll().map(e -> TUserConvert.INSTANCE.domain2dto(e));
        log.info("查询用户列表线程返回");
        return listFlux;
    }

    @GetMapping (path = "{id}")
    public Mono<TUserDto> findById(@PathVariable("id") long id) {
        return tUserService.findById(id).map(e -> TUserConvert.INSTANCE.domain2dto(e));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TUserDto> save(@RequestBody Mono<TUserDto> tUserDto) {
        return tUserService.save(tUserDto.map(item -> TUserConvert.INSTANCE.dtoToDomain(item))).map(e -> TUserConvert.INSTANCE.domain2dto(e));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update (@RequestBody Mono<TUserDto> tUserDto) {
        return tUserService.update(tUserDto.map(item -> TUserConvert.INSTANCE.dtoToDomain(item)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete (@RequestBody Mono<TUserDto> tUserDto) {
        return tUserService.delete(tUserDto.map(item -> TUserConvert.INSTANCE.dtoToDomain(item)));
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById (@PathVariable long id) {
        return tUserService.deleteById(id);
    }
}
