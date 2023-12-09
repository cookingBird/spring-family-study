package com.example.annotationtransactiondemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class AnnotationTransactionDemoApplication implements CommandLineRunner {

    private FooService fooService;
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(AnnotationTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("DELETE FROM FOO");
        log.info("AAA {}",
                jdbcTemplate
                        .queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
        try {
            fooService.invokeInsertThenRollback();
        } catch (Exception e) {

        }


        log.info("AAA {}",
                jdbcTemplate
                        .queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
    }
}
