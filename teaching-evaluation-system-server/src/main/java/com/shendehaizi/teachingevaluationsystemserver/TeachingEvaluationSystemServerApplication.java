package com.shendehaizi.teachingevaluationsystemserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.shendehaizi")
public class TeachingEvaluationSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingEvaluationSystemServerApplication.class, args);
    }

}
