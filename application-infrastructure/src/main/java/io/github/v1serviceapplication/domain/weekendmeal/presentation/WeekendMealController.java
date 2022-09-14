package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.PostWeekendMealApplyRequest;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final WeekendMealApi weekendMealApi;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postWeekendMealApply(@RequestBody @Valid PostWeekendMealApplyRequest request) {
        weekendMealApi.postWeekendMealApply(request.getApply());
    }

    @GetMapping
    public QueryWeekendMealResponse queryWeekendMeal() {
        return weekendMealApi.queryWeekendMeal();
    }

}
