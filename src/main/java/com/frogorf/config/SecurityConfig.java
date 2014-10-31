package com.frogorf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by alex on 31.10.14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder aut) throws Exception {
        aut.userDetailsService(userDetailsService);
        //userService.getUser(aut.getN)
        //auth.inMemoryAuthentication().withUser("1").password("1").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll();
        http
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated();
        http
                .csrf().disable()
                .formLogin().loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
