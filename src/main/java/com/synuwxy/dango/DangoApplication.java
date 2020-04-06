package com.synuwxy.dango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wxy
 */
@EnableAsync
@SpringBootApplication
public class DangoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangoApplication.class, args);
    }

}
