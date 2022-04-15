package com.axiomq.starwars.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("swapi")
@PropertySource(value = {"classpath:application.properties"})
@Getter
@Setter
public class AppProperties {

    private String url;
    private String planets;
    private String films;
}
