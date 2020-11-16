package com.jadan.client.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ApplicationConfiguration {

//    @Bean
//    public javax.validation.Validator validator() {
//        final LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
//        factory.setValidationMessageSource(messageSource());
//        return factory;
//    }
//
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        messageSource.setUseCodeAsDefaultMessage(true);
//
//        return messageSource;
//    }

//    @Bean
//    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
//        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//
//        reloadableResourceBundleMessageSource.setBasename("classpath:messages");
//        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
//        reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
//
//        return reloadableResourceBundleMessageSource;
//    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
}
