package com.example.mongodemo;

import com.example.mongodemo.converter.MoneyConverter;
import com.example.mongodemo.model.Coffee;
import com.mongodb.client.result.UpdateResult;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
public class MongoDemoApplication implements ApplicationRunner {

    @Lazy
    @Autowired
    private MongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.00))
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        Coffee saved = mongoTemplate.save(espresso);
        log.info("Coffee: {}", saved);

        List<Coffee> list = mongoTemplate.find(
                Query.query(Criteria.where("name").is("espresso")),
                Coffee.class
        );
        log.info("Find {} Coffee", list.size());
        list.forEach(coffee -> {
            log.info("Coffee {}", coffee);
        });

        Thread.sleep(1000);
        UpdateResult res = mongoTemplate.updateFirst(
                Query.query(Criteria.where("name").is("espresso")),
                new Update().set("price", Money.of(CurrencyUnit.of("CNY"), 50)),
                Coffee.class
        );

        log.info("update res: {}", res.getModifiedCount());
        Coffee updated = mongoTemplate.findById(saved.getId(), Coffee.class);
        log.info("update Result: {}", updated);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Collections.singletonList(new MoneyConverter()));
    }
}
