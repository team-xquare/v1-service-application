package io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWeekendMealApplyRequest {

    @NotNull(message = "status는 null일 수 없습니다.")
    private WeekendMealApplicationStatus status;

}
