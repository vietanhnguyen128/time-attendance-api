//package com.example.TimeAttendanceAPI.Interceptor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Component
//public class RequestInterceptorAppConfig implements WebMvcConfigurer {
//
//    @Autowired
//    RequestInterceptor requestInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestInterceptor)
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/docs/**");
//    }
//}
