package com.example.druiddemo;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;


@Component
@Slf4j
public class ConnectLogFilter extends FilterEventAdapter {

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.warn("BEFORE CONNECTION!");
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.warn("AFTER CONNECTION!");
    }
}
