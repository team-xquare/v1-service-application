package io.github.v1serviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class V1ServiceApplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(V1ServiceApplicationApplication.class, args);
    }
}
