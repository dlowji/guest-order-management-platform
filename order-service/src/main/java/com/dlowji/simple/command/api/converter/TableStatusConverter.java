package com.dlowji.simple.command.api.converter;

import com.dlowji.simple.command.api.enums.TableStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TableStatusConverter implements AttributeConverter<TableStatus, String> {
    @Override
    public String convertToDatabaseColumn(TableStatus tableStatus) {
        if (tableStatus == null) {
            return null;
        }

        return tableStatus.toString();
    }

    @Override
    public TableStatus convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(TableStatus.values())
                .filter(c -> c.toString().equals(s))
                .findFirst()
                .get();
    }
}
