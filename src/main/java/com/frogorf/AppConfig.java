package com.frogorf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.frogorf.controller", "com.frogorf.config"})
public class AppConfig {

}
