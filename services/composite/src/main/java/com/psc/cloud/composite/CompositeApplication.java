package com.psc.cloud.composite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.psc.cloud")// api Module도 Include가능하도록
@SpringBootApplication
public class CompositeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompositeApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
