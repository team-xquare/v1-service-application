package io.github.v1serviceapplication.weekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;

import java.util.List;
import java.util.UUID;

public interface QueryWeekendMealApplyRepositorySpi {
    boolean queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId);


    List<UUID> queryWeekendMealUserList();

    List<WeekendMealApply> findAll();

}
