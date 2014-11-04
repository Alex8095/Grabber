package com.frogorf.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class, HibernateConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class, ThymeleafConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setInitParameter("dispatchOptionsRequest", "true");
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter charFilter = new CharacterEncodingFilter();
        charFilter.setEncoding("UTF-8");
        charFilter.setForceEncoding(true);
        return new Filter[]{new HiddenHttpMethodFilter(), charFilter, new HttpPutFormContentFilter()};
    }

}
