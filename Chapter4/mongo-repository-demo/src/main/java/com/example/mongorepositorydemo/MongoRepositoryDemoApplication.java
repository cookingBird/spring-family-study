package com.example.mongorepositorydemo;

import com.example.mongorepositorydemo.converter.MongoReadConverter;
import com.example.mongorepositorydemo.model.Coffee;
import com.example.mongorepositorydemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class MongoRepositoryDemoApplication implements ApplicationRunner {

    @Lazy
    @Autowired
    CoffeeRepository coffeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoRepositoryDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .createTime(new Date())
                .updateTime(new Date()).build();
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .createTime(new Date())
                .updateTime(new Date()).build();

        coffeeRepository.insert(Arrays.asList(espresso, latte));
        coffeeRepository.findAll(Sort.by("name"))
                .forEach(c -> log.info("Saved Coffee {}", c));

        Thread.sleep(1000);
        latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
        latte.setUpdateTime(new Date());
        coffeeRepository.save(latte);
        coffeeRepository.findByName("latte")
                .forEach(c -> log.info("Coffee {}", c));

        coffeeRepository.deleteAll();
    }

    @Bean
    public MongoCustomConversions mogoCustomConversions() {
        return new MongoCustomConversions(Collections.singletonList(new MongoReadConverter()));
    }
}
