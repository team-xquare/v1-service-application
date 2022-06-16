package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.domain.weekendmeal.presentation.dto.request.PostWeekendMealApplyRequest;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.api.PostWeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.queryweekendmeal.api.QueryWeekendMeal;
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

    private final PostWeekendMealApply postWeekendMealApply;
    private final QueryWeekendMeal queryWeekendMeal;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postWeekendMealApply(@RequestBody @Valid PostWeekendMealApplyRequest request) {
        postWeekendMealApply.postWeekendMealApply(request.getApply());
    }

    @GetMapping
    public String queryWeekendMeal() {
        return queryWeekendMeal.queryWeekendMeal();
    }

}
