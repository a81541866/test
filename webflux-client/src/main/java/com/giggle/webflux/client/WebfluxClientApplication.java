package com.giggle.webflux.client;

import com.giggle.webflux.invoke.annotation.EnableWebfluxClientInvoke;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableWebfluxClientInvoke(componentScan = "com.giggle.webflux.api")
@SpringBootApplication
public class WebfluxClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxClientApplication.class, args);
	}

}
