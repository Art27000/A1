package com.example.csvloader.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*Переводит dd-mm-yyyy в ISO*/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final DateTimeFormatter DTF =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (source == null || source.isEmpty()) {
                    return null;
                }
                return LocalDate.parse(source, DTF);
            }
        });
    }
}
