package io.github.v1serviceapplication.weekendmeal.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.user.dto.response.UserInfoElement;
import io.github.v1serviceapplication.user.spi.UserFeignSpi;
import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;
import io.github.v1serviceapplication.weekendmeakcheck.spi.PostWeekendMealCheckRepositorySpi;
import io.github.v1serviceapplication.weekendmeakcheck.spi.QueryWeekendMealCheckRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.api.WeekendMealApi;
import io.github.v1serviceapplication.weekendmeal.api.dto.QueryWeekendMealResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.github.v1serviceapplication.weekendmeal.exception.NonResponseStatusIsImpossibleException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealCanNotApplicationException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class WeekendMealApiImpl implements WeekendMealApi {

    private final PostWeekendMealApplyRepositorySpi postWeekendMealApplyRepositorySpi;
    private final QueryWeekendMealRepositorySpi queryWeekendMealRepositorySpi;
    private final QueryWeekendMealApplyRepositorySpi queryWeekendMealApplyRepositorySpi;
    private final PostWeekendMealCheckRepositorySpi postWeekendMealCheckRepositorySpi;
    private final QueryWeekendMealCheckRepositorySpi queryWeekendMealCheckRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final UserFeignSpi userFeignSpi;

    @Override
    public void postWeekendMealApply(WeekendMealApplicationStatus status) {
        checkWeekendMealValidTerm();
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();
        UUID userId = userIdFacade.getCurrentUserId();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }
        checkWeekendMealValidStatus(status);

        postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, weekendMeal.getId(), status);
    }

    private void checkWeekendMealValidStatus(WeekendMealApplicationStatus requestStatus) {
        if (requestStatus == WeekendMealApplicationStatus.NON_RESPONSE) {
            throw NonResponseStatusIsImpossibleException.EXCEPTION;
        }
    }

    private void checkWeekendMealValidTerm() {
        int nowDate = LocalDate.now().atStartOfDay().getDayOfMonth();
        int weekendMealApplicationDate = LocalDate.now().withDayOfMonth(3).getDayOfMonth();

        if (nowDate > weekendMealApplicationDate) {
            throw WeekendMealCanNotApplicationException.EXCEPTION;
        }
    }

    @Override
    public QueryWeekendMealResponse queryWeekendMeal() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        WeekendMealApplicationStatus status = queryWeekendMealApplyRepositorySpi.queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(userIdFacade.getCurrentUserId(), weekendMeal.getId());
        return new QueryWeekendMealResponse(weekendMeal.getTitle(), status);

    }

    @Override
    public WeekendMealListResponse queryWeekendMealUserList(Integer grade, Integer classNum) {
        UUID teacherId = userIdFacade.getCurrentUserId();
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();
        List<WeekendMealApply> weekendMealApplies = queryWeekendMealApplyRepositorySpi.findWeekendMealListByWeekendMealId(weekendMeal.getId());
        List<UUID> userIds = queryWeekendMealApplyRepositorySpi.queryWeekendMealUserList();
        Optional<WeekendMealCheck> weekendMealCheck = queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckByWeekendMealIdAndUserId(weekendMeal.getId(), teacherId);
        boolean isCheck = weekendMealCheck.map(WeekendMealCheck::isCheck).orElse(false);

        if (userIds.isEmpty()) {
            return new WeekendMealListResponse(isCheck, List.of(), List.of());
        }

        Map<UUID, UserInfoElement> hashMap =
                userFeignSpi.getUserInfoList(userIds).stream().collect(Collectors.toMap(UserInfoElement::getUserId,
                        user -> user, (userId, user) -> user, HashMap::new));

        List<WeekendMealElement> weekendMealResponseElements = new ArrayList<>();
        List<WeekendMealElement> weekendMealNonResponseElements = new ArrayList<>();

        if (grade != null || classNum != null) {
            weekendMealApplies.stream().filter(weekendMealApply -> {
                UserInfoElement user = hashMap.get(weekendMealApply.getUserId());
                Integer userGrade = Integer.valueOf(user.getNum().substring(0, 1));
                Integer userClassNum = Integer.valueOf(user.getNum().substring(1, 2));

                if (grade != null && classNum == null) {
                    return grade.equals(userGrade);
                } else if (grade == null && classNum != null) {
                    return classNum.equals(userClassNum);
                } else {
                    return grade.equals(userGrade) && classNum.equals(userClassNum);
                }
            }).map(weekendMealApply -> {
                UserInfoElement user = hashMap.get(weekendMealApply.getUserId());
                return buildWeekendMealElement(user, weekendMealApply.getStatus(), weekendMealResponseElements, weekendMealNonResponseElements);
            }).sorted(Comparator.comparing(WeekendMealElement::getNum)).toList();
        } else {
            weekendMealApplies.stream().map(weekendMealApply -> {
                UserInfoElement user = hashMap.get(weekendMealApply.getUserId());

                return buildWeekendMealElement(user, weekendMealApply.getStatus(), weekendMealResponseElements,
                        weekendMealNonResponseElements);
            }).sorted(Comparator.comparing(WeekendMealElement::getNum)).toList();
        }

        return new WeekendMealListResponse(isCheck, weekendMealResponseElements, weekendMealNonResponseElements);
    }

    @Override
    public void postWeekendMealCheck(boolean isCheck) {
        UUID teacherId = userIdFacade.getCurrentUserId();
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMealByDate();
        WeekendMealCheck weekendMealCheck = WeekendMealCheck.builder()
                .weekendMealId(weekendMeal.getId())
                .createDate(LocalDate.now())
                .userId(teacherId)
                .isCheck(isCheck).build();

        weekendMealCheckSaveOrUpdate(weekendMeal.getId(), teacherId, weekendMealCheck);
    }
    private void weekendMealCheckSaveOrUpdate(UUID weekendMealId, UUID userId, WeekendMealCheck weekendMealCheck) {
        Optional<WeekendMealCheck> weekendMealCheckDomain = queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckByWeekendMealIdAndUserId(weekendMealId, userId);

        if (queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckByWeekendMealIdAndUserId(weekendMealId, userId) != null) {
            postWeekendMealCheckRepositorySpi.changeWeekendMealIsCheck(
                    weekendMealCheckDomain.get().getId(), weekendMealCheck.isCheck()
            );
        } else {
            postWeekendMealCheckRepositorySpi.postWeekendMealCheck(weekendMealCheck);
        }
    }

    private WeekendMealElement buildWeekendMealElement(UserInfoElement user, WeekendMealApplicationStatus status,
                                                       List<WeekendMealElement> weekendMealResponseElements,
                                                       List<WeekendMealElement> weekendMealNonResponseElements
    ) {
        WeekendMealElement weekendMealElement = WeekendMealElement.builder()
                .id(user.getUserId())
                .num(user.getNum())
                .name(user.getName())
                .status(status).build();
        
        addWeekendMealList(weekendMealElement, status, weekendMealResponseElements, weekendMealNonResponseElements);

        return weekendMealElement;
    }

    private void addWeekendMealList(WeekendMealElement weekendMealElement, WeekendMealApplicationStatus status,
                                    List<WeekendMealElement> weekendMealResponseElements,
                                    List<WeekendMealElement> weekendMealNonResponseElements) {
        if (status.equals(WeekendMealApplicationStatus.APPLY) || status.equals(WeekendMealApplicationStatus.NOT_APPLY)) {
            weekendMealResponseElements.add(weekendMealElement);
        } else {
            weekendMealNonResponseElements.add(weekendMealElement);
        }
    }
}
