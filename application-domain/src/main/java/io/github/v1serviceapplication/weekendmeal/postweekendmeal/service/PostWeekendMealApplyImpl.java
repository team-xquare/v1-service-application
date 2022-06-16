package io.github.v1serviceapplication.weekendmeal.postweekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@DomainService
public class PostWeekendMealApplyImpl implements PostWeekendMealApply {

    private PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;

    @Override
    public void postWeekendMealApply(boolean apply) {
        UUID userId = UUID.randomUUID(); //TODO userId 가져오기

        //TODO 주말급식 비즈니스로직
        postWeekendMealApplyRepositorySpi.saveWeekendMealApply(
                WeekendMealApply.builder()
                        .userId(userId)
                        .isApplied(apply)
                        .build()
        );
    }
}
