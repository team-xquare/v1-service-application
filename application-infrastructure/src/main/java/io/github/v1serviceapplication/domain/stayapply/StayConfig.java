package io.github.v1serviceapplication.domain.stayapply;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.stayapply.Stay;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = {Stay.class},
        includeFilters = { @ComponentScan.Filter(
        type = FilterType.ANNOTATION,
        value = DomainService.class
)})
public class StayConfig {}
