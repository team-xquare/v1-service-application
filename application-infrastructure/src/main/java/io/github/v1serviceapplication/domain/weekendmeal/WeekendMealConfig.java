package io.github.v1serviceapplication.domain.weekendmeal;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = {WeekendMeal.class},
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = DomainService.class
                )
        }
)
public class WeekendMealConfig {
}
