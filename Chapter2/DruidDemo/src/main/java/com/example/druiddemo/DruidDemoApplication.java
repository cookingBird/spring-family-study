package com.example.druiddemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class DruidDemoApplication implements CommandLineRunner {

    DataSource dataSource;

    FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(DruidDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("datasource:\n"+dataSource.toString());

        new Thread(() -> fooService.selectForUpdate()).start();
        new Thread(() -> fooService.selectForUpdate()).start();
    }
}
