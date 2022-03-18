package com.caomei.knowledge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.caomei.knowledge.mapper")
@EnableAsync
public class CaoMeiKonwledge extends SpringBootServletInitializer {

        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(CaoMeiKonwledge.class);
        }

        public static void main(String[] args) {
            SpringApplication.run(CaoMeiKonwledge.class, args);
        }
}
