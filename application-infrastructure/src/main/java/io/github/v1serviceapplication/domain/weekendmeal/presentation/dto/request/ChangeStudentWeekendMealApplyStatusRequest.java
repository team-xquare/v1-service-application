package io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChangeStudentWeekendMealApplyStatusRequest {
    @NotNull
    private UUID studentId;

    @NotNull
    private WeekendMealApplicationStatus status;
}
