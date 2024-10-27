package pl.edu.wszib.media.store.config;

import jakarta.annotation.Resources;
import pl.edu.wszib.media.store.dao.ITripDAO;
import pl.edu.wszib.media.store.filters.AdminFilter;
import pl.edu.wszib.media.store.services.ITripService;
import pl.edu.wszib.media.store.services.impl.TripService;

import org.hibernate.SessionFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("pl.edu.wszib.media.store")
public class AppConfiguration {

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/trip/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
