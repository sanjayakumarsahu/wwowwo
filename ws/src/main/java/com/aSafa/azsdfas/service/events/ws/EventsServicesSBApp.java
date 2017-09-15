
package com.geaviation.eds.service.events.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.geaviation.eds.service.events.ws.interceptor.LoggingInterceptor;

@Configuration
@ComponentScan("com.geaviation.*")
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@PropertySources(value={@PropertySource("classpath:events.properties"),@PropertySource("classpath:service.commons.properties")})
public class EventsServicesSBApp {

  public static void main(String[] args) {
    SpringApplication.run(EventsServicesSBApp.class, args);
  }
  
  @Bean
  public WebMvcConfigurerAdapter adapter() {
      return new WebMvcConfigurerAdapter() {
          @Override
          public void addInterceptors(InterceptorRegistry registry) {
              registry.addInterceptor(new LoggingInterceptor());
              
              super.addInterceptors(registry);
          }
          
      };
  }

}
