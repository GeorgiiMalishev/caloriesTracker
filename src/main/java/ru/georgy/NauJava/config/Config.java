package ru.georgy.NauJava.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.georgy.NauJava.model.Product;

@Configuration
public class Config {
    private final String appName;
    private final String appVersion;

    public Config(@Value("${app.name}") String appName,
                  @Value("${app.version}") String appVersion) {
        this.appName = appName;
        this.appVersion = appVersion;
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Product> productContainer() {
        return new ArrayList<>();
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getAppName() {
        return appName;
    }
}
