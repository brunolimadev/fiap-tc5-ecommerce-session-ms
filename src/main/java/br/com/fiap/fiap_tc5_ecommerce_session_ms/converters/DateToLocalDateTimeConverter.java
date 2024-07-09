package br.com.fiap.fiap_tc5_ecommerce_session_ms.converters;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    @Override
    public LocalDateTime convert(Date source) {
        return LocalDateTime.ofInstant(source.toInstant(), ZoneId.of("GMT-3"));
    }
}