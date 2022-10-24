package io.github.v1serviceapplication.infrastructure.feign;

import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "io.github.v1serviceapplication.infrastructure.feign")
@Configuration
public class FeignConfig {

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientErrorDecoder commonFeignErrorDecoder()  {
        return new FeignClientErrorDecoder();
    }

}
