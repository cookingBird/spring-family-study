package com.example.druiddemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class FooService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void selectForUpdate() {
        Object f = jdbcTemplate.queryForObject("select BAR from FOO where ID = 1", Object.class);
        System.out.println("f is:\n" + f.toString());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error("InterruptedException", e);
        }
    }
}
