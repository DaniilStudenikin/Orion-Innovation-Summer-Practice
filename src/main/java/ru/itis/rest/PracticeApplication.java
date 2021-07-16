package ru.itis.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
//@EnableSwagger2
public class PracticeApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("ru.itis.rest.controllers"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

}
