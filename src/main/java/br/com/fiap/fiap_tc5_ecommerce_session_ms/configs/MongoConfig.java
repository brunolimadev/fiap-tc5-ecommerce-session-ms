package br.com.fiap.fiap_tc5_ecommerce_session_ms.configs;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.converters.DateToLocalDateTimeConverter;
import br.com.fiap.fiap_tc5_ecommerce_session_ms.converters.LocalDateTimeToDateConverter;

@Configuration
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context) {
        MappingMongoConverter converter = new MappingMongoConverter(factory, context);
        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();
        return converter;
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = Arrays.asList(
                new LocalDateTimeToDateConverter(),
                new DateToLocalDateTimeConverter());
        return new MongoCustomConversions(converters);
    }
}
