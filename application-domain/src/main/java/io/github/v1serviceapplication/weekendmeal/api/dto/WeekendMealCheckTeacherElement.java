package io.github.v1serviceapplication.weekendmeal.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WeekendMealCheckTeacherElement {
    private final String name;
    private final int grade;
    private final int classNum;
    private final LocalDate createDate;
}
