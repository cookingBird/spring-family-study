package com.example.annotationtransactiondemo;

public interface FooService {
    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;
}
