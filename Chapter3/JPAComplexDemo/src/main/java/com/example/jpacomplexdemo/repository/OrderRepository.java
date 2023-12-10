package com.example.jpacomplexdemo.repository;

import com.example.jpacomplexdemo.model.CoffeeOrder;

import java.util.List;

public interface OrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByConsumerOrderById(String customer);

    List<CoffeeOrder> findByItems_Name(String name);
}
