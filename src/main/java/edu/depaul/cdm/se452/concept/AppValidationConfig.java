package edu.depaul.cdm.se452.concept;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class AppValidationConfig {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource msg = new ReloadableResourceBundleMessageSource();

        msg.setBasename("classpath:messages");
        msg.setDefaultEncoding("UTF-8");
        return msg;
    }     
}
