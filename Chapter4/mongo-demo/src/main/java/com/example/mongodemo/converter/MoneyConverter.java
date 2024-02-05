package com.example.mongodemo.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

public class MoneyConverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document source) {
        Document money = (Document) source.get("money");
        double amount = Double.parseDouble(money.getString("amount"));
        String currencyCode = ((Document) money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currencyCode), amount);
    }
}
