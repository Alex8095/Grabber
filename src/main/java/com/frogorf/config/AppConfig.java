package com.frogorf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.frogorf.web.controller", "com.frogorf.config"})
public class AppConfig {

}
