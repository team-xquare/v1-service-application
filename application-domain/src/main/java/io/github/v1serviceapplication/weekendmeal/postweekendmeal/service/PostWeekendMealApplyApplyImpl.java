package io.github.v1serviceapplication.weekendmeal.postweekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class PostWeekendMealApplyApplyImpl implements PostWeekendMealApply {

    private PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;

    @Override
    public void postWeekendMeal(boolean apply) {
        UUID userId = UUID.randomUUID(); //TODO userId 가져오기

        //TODO 주말급식 비즈니스로직
        postWeekendMealApplyRepositorySpi.saveWeekendMeal(
                WeekendMeal.builder()
                        .userId(userId)
                        .isApplied(apply)
                        .build()
        );
    }
}
