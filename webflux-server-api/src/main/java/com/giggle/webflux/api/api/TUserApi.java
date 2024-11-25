package com.giggle.webflux.api.api;

import com.giggle.webflux.annotation.WebFluxClient;
import com.giggle.webflux.api.dto.TUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@WebFluxClient(value = "http://localhost:8080/")
@RequestMapping("/user")
public interface TUserApi {

    @GetMapping
    Flux<TUserDto> findAll();

    @GetMapping (path = "/{id}")
    Mono<TUserDto> findById(@PathVariable("id") long id);


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<TUserDto> save(@RequestBody Mono<TUserDto> employee);

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> update (@RequestBody Mono<TUserDto> employee);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> delete (@RequestBody Mono<TUserDto> employee);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> deleteById (@PathVariable("id") long id);

}
