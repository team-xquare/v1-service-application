package io.github.v1serviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
@EnableSwagger2
public class V1ServiceApplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(V1ServiceApplicationApplication.class, args);
    }
}
