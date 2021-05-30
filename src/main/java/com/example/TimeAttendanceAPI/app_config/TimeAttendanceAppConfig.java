package com.example.TimeAttendanceAPI.app_config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(title = "Time Attendance API", version = "v1.0")
)
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
