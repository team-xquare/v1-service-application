package io.github.v1serviceapplication.domain.reservation;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.reservation.PicnicReservation;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackageClasses = {PicnicReservation.class},
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = DomainService.class
        )}
)
public class PicnicReservationConfig {
}
