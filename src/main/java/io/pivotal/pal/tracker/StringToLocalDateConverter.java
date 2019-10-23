package io.pivotal.pal.tracker;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.time.LocalDate;

public class StringToLocalDateConverter extends StdConverter<String, LocalDate> {

    @Override
    public LocalDate convert(String value) {
        return null;
    }

}
