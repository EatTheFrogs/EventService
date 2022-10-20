package com.eatthefrog.EventService.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Component
@WritingConverter
public class DateWritingConverter implements Converter<ZonedDateTime, Date> {

    @Override
    public Date convert(@Nullable ZonedDateTime zonedDateTime) {
        if(Objects.isNull(zonedDateTime)) {
            return null;
        }

        return Date.from(zonedDateTime.toInstant());
    }
}
