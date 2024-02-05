package com.example.mongorepositorydemo.converter;

import org.bson.Document;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;


public class MongoReadConverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document source) {
        return null;
    }
}
