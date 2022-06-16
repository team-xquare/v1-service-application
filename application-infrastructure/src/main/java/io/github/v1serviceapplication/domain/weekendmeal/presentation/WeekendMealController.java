package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.PostWeekendMealApplyRequest;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMealApply;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/weekend-meal")
@RestController
public class WeekendMealController {

    private final PostWeekendMealApply postWeekendMealApply;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postWeekendMeal(@RequestBody @Valid PostWeekendMealApplyRequest request) {
        postWeekendMealApply.postWeekendMeal(request.getApply());
    }

}
