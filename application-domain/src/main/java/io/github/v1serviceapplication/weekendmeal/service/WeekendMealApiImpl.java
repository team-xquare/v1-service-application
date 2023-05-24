package io.github.v1serviceapplication.weekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.user.dto.response.UserInfoElement;
import io.github.v1serviceapplication.user.spi.UserFeignSpi;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.github.v1serviceapplication.weekendmeal.exception.NonResponseRequestImpossibleException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealCanNotApplicationException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class WeekendMealApiImpl implements WeekendMealApi {

    private final PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;
    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;
    private final QueryWeekendMealApplyRepositorySpi queryWeekendMealApplyRepositorySpi;
    private final UserIdFacade userIdFacade;

    private final UserFeignSpi userFeignSpi;

    @Override
    public void postWeekendMealApply(WeekendMealApplicationStatus status) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();
        UUID userId = userIdFacade.getCurrentUserId();

        checkWeekendMealValidTerm();
        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }
        checkWeekendMealValidStatus(status);

        postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, weekendMeal.getId(), status);
    }

    private void checkWeekendMealValidStatus(WeekendMealApplicationStatus requestStatus) {
        if(requestStatus == WeekendMealApplicationStatus.NONRESPONSE) {
            throw NonResponseRequestImpossibleException.EXCEPTION;
        }
    }

    private void checkWeekendMealValidTerm() {
        int nowDate = LocalDate.now().atStartOfDay().getDayOfMonth();
        int weekendMealApplicationDate = LocalDate.now().withDayOfMonth(3).getDayOfMonth();

        if(nowDate > weekendMealApplicationDate) {
            throw WeekendMealCanNotApplicationException.EXCEPTION;
        }
    }

    @Override
    public QueryWeekendMealResponse queryWeekendMeal() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        WeekendMealApplicationStatus status = queryWeekendMealApplyRepositorySpi
                .queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(userIdFacade.getCurrentUserId(), weekendMeal.getId());

        return new QueryWeekendMealResponse(
                weekendMeal.getTitle(),
                status
        );

    }

    @Override
    public WeekendMealListResponse queryWeekendMealUserList() {
        List<WeekendMealApply> weekendMeals = queryWeekendMealApplyRepositorySpi.findAll();
        List<UUID> userIds = queryWeekendMealApplyRepositorySpi.queryWeekendMealUserList();

        if (userIds.isEmpty()) {
            return new WeekendMealListResponse(List.of());
        }

        Map<UUID, UserInfoElement> hashMap = userFeignSpi.getUserInfoList(userIds).stream()
                .collect(Collectors.toMap(UserInfoElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<WeekendMealElement> weekendMealElements = weekendMeals.stream()
                .map(weekendMeal -> {
                    UserInfoElement user = hashMap.get(weekendMeal.getUserId());
                    return WeekendMealElement.builder()
                            .id(user.getUserId())
                            .num(user.getNum())
                            .name(user.getName())
                            .status(weekendMeal.getStatus())
                            .build();
                }).sorted(Comparator.comparing(WeekendMealElement::getNum))
                .toList();

        return new WeekendMealListResponse(weekendMealElements);
    }
}
