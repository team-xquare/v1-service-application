package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.PostWeekendMealRequest;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMeal;
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

    private final PostWeekendMeal postWeekendMeal;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postWeekendMeal(@RequestBody @Valid PostWeekendMealRequest request) {
        postWeekendMeal.postWeekendMeal(request.getApply());
    }

}
