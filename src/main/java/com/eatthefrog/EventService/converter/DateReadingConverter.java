package com.eatthefrog.EventService.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Component
@ReadingConverter
public class DateReadingConverter implements Converter<Date, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(@Nullable Date date) {
        if(Objects.isNull(date)) {
            return null;
        }

        return ZonedDateTime.from(date.toInstant().atZone(ZoneId.of("UTC")));
    }
}
