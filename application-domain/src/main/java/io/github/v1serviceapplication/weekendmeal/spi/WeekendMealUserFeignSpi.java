package io.github.v1serviceapplication.weekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealUserInfoElement;

import java.util.UUID;

public interface WeekendMealUserFeignSpi {
    WeekendMealUserInfoElement queryUserInfo(UUID userId);
}
