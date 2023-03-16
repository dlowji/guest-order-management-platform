package com.dlowji.simple.command.api.converter;

import com.dlowji.simple.command.api.enums.OrderLineItemStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderLineItemStatusConverter implements AttributeConverter<OrderLineItemStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderLineItemStatus orderLineItemStatus) {
        if (orderLineItemStatus == null) {
            return null;
        }
        return orderLineItemStatus.toString();
    }

    @Override
    public OrderLineItemStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(OrderLineItemStatus.values())
                .filter(c -> c.toString().equalsIgnoreCase(s))
                .findFirst()
                .get();
    }
}
