package com.example.springbucks;

import com.example.springbucks.model.Coffee;
import com.example.springbucks.model.CoffeeOrder;
import com.example.springbucks.model.OrderState;
import com.example.springbucks.repository.CoffeeRepository;
import com.example.springbucks.service.CoffeeOrderService;
import com.example.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
@EnableTransactionManagement
public class SpringbucksApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService coffeeOrderService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbucksApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("All Coffee: {}", coffeeRepository.findAll());
        Optional<Coffee> latte = coffeeService.findOneCoffee("latte");
        Optional<Coffee> mocha = coffeeService.findOneCoffee("mocha");
        if (latte.isPresent() && mocha.isPresent()) {
            CoffeeOrder order = coffeeOrderService.createOrder("Li Lei", latte.get(), mocha.get());
            log.info("Update INIT to PAID: {}", coffeeOrderService.updateState(order, OrderState.PAID));
            log.info("Update INIT to INIT: {}", coffeeOrderService.updateState(order, OrderState.INIT));
        }

    }
}
