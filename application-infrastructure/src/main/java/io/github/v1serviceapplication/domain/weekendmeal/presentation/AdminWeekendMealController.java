package io.github.v1serviceapplication.domain.weekendmeal.presentation;

import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민 주말급식 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminWeekendMealController {

    private final WeekendMealApi weekendMealApi;

    @Operation(summary = "주말급식 리스트 API")
    @GetMapping("/weekend-meal")
    public WeekendMealListResponse weekendMealUserList() {
        return weekendMealApi.queryWeekendMealUserList();
    }
}
