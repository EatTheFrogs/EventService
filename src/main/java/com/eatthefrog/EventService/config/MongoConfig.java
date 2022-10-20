package com.eatthefrog.EventService.config;

import com.eatthefrog.EventService.converter.DateReadingConverter;
import com.eatthefrog.EventService.converter.DateWritingConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final DateWritingConverter dateWritingConverter;
    private final DateReadingConverter dateReadingConverter;

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
            Arrays.asList(
                dateWritingConverter,
                dateReadingConverter));
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
