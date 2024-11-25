package com.giggle.webflux.api.api;

import com.giggle.webflux.annotation.WebFluxClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@WebFluxClient(value = "http://localhost:8080/")
@RequestMapping("/world")
public interface TestApi {

    @GetMapping
    Flux<String> sayHi();
}