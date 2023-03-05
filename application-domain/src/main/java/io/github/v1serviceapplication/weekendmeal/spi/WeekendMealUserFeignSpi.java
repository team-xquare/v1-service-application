package io.github.v1serviceapplication.weekendmeal.spi;

import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealUserElement;

import java.util.List;
import java.util.UUID;

public interface WeekendMealUserFeignSpi {
    List<WeekendMealUserElement> getUserInfoList(List<UUID> ids);
}
