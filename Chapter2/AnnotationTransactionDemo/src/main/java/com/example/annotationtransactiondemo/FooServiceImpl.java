package com.example.annotationtransactiondemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Lazy
public class FooServiceImpl implements FooService {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    @Lazy
//    private FooService fooService;


    private final JdbcTemplate jdbcTemplate;
    private final FooService fooService;

    @Autowired
    public FooServiceImpl(JdbcTemplate jdbcTemplate, @Lazy FooService fooService) {
        this.jdbcTemplate = jdbcTemplate;
        this.fooService = fooService;
    }

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
//    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRED)
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.REQUIRES_NEW)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
        throw new RollbackException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void invokeInsertThenRollback() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
        try {
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.error("invokeInsertThenRollback RollbackException");
        }
    }
}
