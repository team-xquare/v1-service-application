package io.github.v1serviceapplication.weekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.user.dto.response.UserInfoElement;
import io.github.v1serviceapplication.user.spi.UserFeignSpi;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;
import io.github.v1serviceapplication.weekendmealcheck.exception.WeekendMealAlreadyCheckException;
import io.github.v1serviceapplication.weekendmealcheck.spi.PostWeekendMealCheckSpi;
import io.github.v1serviceapplication.weekendmealcheck.spi.QueryWeekendMealCheckRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class WeekendMealApiImpl implements WeekendMealApi {

    private final PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;
    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;
    private final QueryWeekendMealApplyRepositorySpi queryWeekendMealApplyRepositorySpi;
    private final QueryWeekendMealCheckRepositorySpi queryWeekendMealCheckRepositorySpi;
    private final PostWeekendMealCheckSpi postWeekendMealCheckSpi;
    private final UserIdFacade userIdFacade;

    private final UserFeignSpi userFeignSpi;

    @Override
    public void postWeekendMealApply(boolean apply) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        saveOrUpdate(userIdFacade.getCurrentUserId(), weekendMeal.getId(), apply);
    }

    private void saveOrUpdate(UUID userId, UUID weekendMealId, boolean apply) {
        if (postWeekendMealApplyRepositorySpi.currentWeekendMealApplyExist(userId, weekendMealId)) {
            postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, weekendMealId, apply);
        } else {
            postWeekendMealApplyRepositorySpi.saveWeekendMealApply(
                    WeekendMealApply.builder()
                            .userId(userId)
                            .weekendMealId(weekendMealId)
                            .isApplied(apply)
                            .build()
            );
        }
    }

    @Override
    public QueryWeekendMealResponse queryWeekendMeal() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        boolean applied = queryWeekendMealApplyRepositorySpi
                .queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(userIdFacade.getCurrentUserId(), weekendMeal.getId());

        return new QueryWeekendMealResponse(
                weekendMeal.getTitle(),
                applied
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
                            .build();
                }).sorted(Comparator.comparing(WeekendMealElement::getNum))
                .toList();

        return new WeekendMealListResponse(weekendMealElements);
    }

    @Override
    public void weekendMealTeacherCheck(int grade, int classNum) {
        UUID teacherId = userIdFacade.getCurrentUserId();
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if(queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckByTeacherId(teacherId, weekendMeal.getId())) {
            throw WeekendMealAlreadyCheckException.EXCEPTION;
        }

        postWeekendMealCheckSpi.saveWeekendMealCheck(
                WeekendMealCheck.builder()
                        .userId(teacherId)
                        .grade(grade)
                        .classNum(classNum)
                        .weekendMealId(weekendMeal.getId())
                        .build()
        );
    }
}
