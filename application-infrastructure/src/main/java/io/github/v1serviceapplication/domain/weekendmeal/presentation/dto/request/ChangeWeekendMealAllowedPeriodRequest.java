package io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ChangeWeekendMealAllowedPeriodRequest {
    @NotNull
    private Boolean allowedPeriod;
}
