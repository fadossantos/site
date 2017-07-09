package br.com.moradas291.site;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import br.com.moradas291.site.web.conversion.CurrencyFormatter;
import br.com.moradas291.site.web.conversion.DateFormatter;


/**
 * Created by Fernando on 01/07/2017.
 */



@Configuration
public class SiteWebConfig extends WebMvcConfigurerAdapter{

    public SiteWebConfig() {
        super();
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }

    @Bean
    public CurrencyFormatter currencyFormatter(){
        return new CurrencyFormatter();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
