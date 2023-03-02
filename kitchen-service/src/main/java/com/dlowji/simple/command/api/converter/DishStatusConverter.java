package com.dlowji.simple.command.api.converter;

import com.dlowji.simple.command.api.enums.DishStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class DishStatusConverter implements AttributeConverter<DishStatus, String> {

    @Override
    public String convertToDatabaseColumn(DishStatus dishStatus) {
        if (dishStatus == null) {
            return null;
        }
        return dishStatus.toString();
    }

    @Override
    public DishStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(DishStatus.values())
                .filter(c -> c.toString().equalsIgnoreCase(s))
                .findFirst()
                .get();
    }
}
