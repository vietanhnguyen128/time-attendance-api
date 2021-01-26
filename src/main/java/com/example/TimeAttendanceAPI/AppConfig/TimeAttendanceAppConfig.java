package com.example.TimeAttendanceAPI.AppConfig;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class TimeAttendanceAppConfig {

//    @Bean
//    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//
//        //some voodoo black magic that I failed to comprehend, why the hell the unit test failed without this while actual implementation runs flawlessly
//        //Actually kinda get it now, for some reason ObjectMapper can't serialize LocalDate to specified JsonFormat, but then why is the unit test failed while implementation runs flawlessly?
//        //return builder.modulesToInstall(new JavaTimeModule());
//        return builder;
//    }

    // Actually don't need it anymore, but still leave it here in case I encounter similar problem in the future

}
