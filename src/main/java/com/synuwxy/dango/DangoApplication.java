package com.synuwxy.dango;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wxy
 */
@EnableAsync
@EnableSwagger2Doc
@SpringBootApplication
public class DangoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DangoApplication.class, args);
    }

}
