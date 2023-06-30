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
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealAllowedPeriodResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealCheckStatusResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealCheckTeacherElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealElement;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealExcelListResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealListResponse;
import io.github.v1serviceapplication.weekendmeal.api.dto.WeekendMealUserInfoElement;
import io.github.v1serviceapplication.weekendmeal.exception.NonResponseStatusIsImpossibleException;
import io.github.v1serviceapplication.weekendmeal.exception.NotMatchedHomeroomTeacherException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealCanNotApplicationException;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealRepository;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.WeekendMealUserFeignSpi;
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
    private final PostWeekendMealRepository postWeekendMealRepository;
    private final UserIdFacade userIdFacade;
    private final WeekendMealUserFeignSpi weekendMealUserFeignSpi;
    private final UserFeignSpi userFeignSpi;

    @Override
    public void postWeekendMealApply(WeekendMealApplicationStatus status) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        if (weekendMeal.isAllowedPeriod() == false) {
            throw WeekendMealCanNotApplicationException.EXCEPTION;
        }

        UUID userId = userIdFacade.getCurrentUserId();
        WeekendMealUserInfoElement userInfo = weekendMealUserFeignSpi.queryUserInfo(userId);

        boolean existWeekendMealCheck = queryWeekendMealCheckRepositorySpi
                .existWeekendMealCheck(weekendMeal.getId(), userInfo.getGrade(), userInfo.getClassNum());

        if (existWeekendMealCheck) {
            throw WeekendMealCanNotApplicationException.EXCEPTION;
        }

        checkWeekendMealValidStatus(status);
        postWeekendMealApplyRepositorySpi.updateWeekendMealApply(userId, weekendMeal.getId(), status);
    }

    private void checkWeekendMealValidStatus(WeekendMealApplicationStatus requestStatus) {
        if (requestStatus == WeekendMealApplicationStatus.NON_RESPONSE) {
            throw NonResponseStatusIsImpossibleException.EXCEPTION;
        }
    }

    @Override
    public QueryWeekendMealResponse queryWeekendMeal() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        WeekendMealApplicationStatus status = queryWeekendMealApplyRepositorySpi.queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(userIdFacade.getCurrentUserId(), weekendMeal.getId());
        return new QueryWeekendMealResponse(weekendMeal.getTitle(), status);
    }

    @Override
    public WeekendMealListResponse queryWeekendMealUserList(Integer grade, Integer classNum) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        List<WeekendMealApply> weekendMealApplies = queryWeekendMealApplyRepositorySpi.queryWeekendMealListByWeekendMealId(weekendMeal.getId());
        List<UUID> userIds = queryWeekendMealApplyRepositorySpi.queryWeekendMealUserList();

        if (userIds.isEmpty()) {
            return new WeekendMealListResponse(List.of(), List.of());
        }

        Map<UUID, UserInfoElement> userHashMap = userFeignSpi.getUserInfoList(userIds)
                .stream()
                .collect(Collectors.toMap(UserInfoElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<WeekendMealElement> weekendMealResponseElements = new ArrayList<>();
        List<WeekendMealElement> weekendMealNonResponseElements = new ArrayList<>();

        if (grade != null || classNum != null) {
            weekendMealApplies.stream()
                    .filter(weekendMealApply -> {
                        UserInfoElement user = userHashMap.get(weekendMealApply.getUserId());
                        Integer userGrade = Integer.valueOf(user.getNum().substring(0, 1));
                        Integer userClassNum = Integer.valueOf(user.getNum().substring(1, 2));

                        if (grade != null && classNum == null) {
                            return grade.equals(userGrade);
                        } else if (grade == null && classNum != null) {
                            return classNum.equals(userClassNum);
                        } else {
                            return grade.equals(userGrade) && classNum.equals(userClassNum);
                        }
                    })
                    .map(weekendMealApply -> {
                        UserInfoElement user = userHashMap.get(weekendMealApply.getUserId());
                        return buildWeekendMealElement(user, weekendMealApply.getStatus(), weekendMealResponseElements, weekendMealNonResponseElements);
                    }).toList();
        } else {
            weekendMealApplies.stream()
                    .map(weekendMealApply -> {
                        UserInfoElement user = userHashMap.get(weekendMealApply.getUserId());
                        return buildWeekendMealElement(user, weekendMealApply.getStatus(), weekendMealResponseElements, weekendMealNonResponseElements);
                    }).toList();
        }

        return new WeekendMealListResponse(weekendMealResponseElements, weekendMealNonResponseElements);
    }

    @Override
    public void postWeekendMealCheck(boolean isCheck, int grade, int classNum) {
        UUID teacherId = userIdFacade.getCurrentUserId();
        WeekendMealUserInfoElement teacherInfo = weekendMealUserFeignSpi.queryUserInfo(teacherId);

        if (!(teacherInfo.getGrade() == grade && teacherInfo.getClassNum() == classNum)) {
            throw NotMatchedHomeroomTeacherException.EXCEPTION;
        }

        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        WeekendMealCheck weekendMealCheck = WeekendMealCheck.builder()
                .weekendMealId(weekendMeal.getId())
                .createDate(LocalDate.now())
                .userId(teacherId)
                .isCheck(isCheck)
                .grade(teacherInfo.getGrade())
                .classNum(teacherInfo.getClassNum()).build();

        weekendMealCheckSaveOrUpdate(weekendMeal.getId(), teacherId, weekendMealCheck);
    }

    @Override
    public void changeStudentWeekendMealApplyStatus(UUID studentId, WeekendMealApplicationStatus status) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        postWeekendMealApplyRepositorySpi.updateWeekendMealApply(studentId, weekendMeal.getId(), status);
    }

    @Override
    public WeekendMealExcelListResponse weekendMealExcelUserList() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        List<WeekendMealCheck> weekendMealCheckList = queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckListByWeekendMealId(weekendMeal.getId());
        List<UUID> weekendMealTeacherIdList = weekendMealCheckList.stream().map(weekendMealCheck -> weekendMealCheck.getUserId()).toList();

        if (weekendMealCheckList == null) {
            return new WeekendMealExcelListResponse(List.of());
        }

        Map<UUID, UserInfoElement> userHashMap = userFeignSpi.getUserInfoList(weekendMealTeacherIdList).stream()
                .collect(Collectors.toMap(UserInfoElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<WeekendMealCheckTeacherElement> teacherLists = weekendMealCheckList.stream()
                .map(weekendMealCheck -> {
                    UserInfoElement user = userHashMap.get(weekendMealCheck.getUserId());

                    return WeekendMealCheckTeacherElement.builder()
                            .name(user.getName())
                            .grade(Integer.parseInt(user.getNum().substring(0, 1)))
                            .classNum(Integer.parseInt(user.getNum().substring(1, 2)))
                            .createDate(weekendMealCheck.getCreateDate())
                            .build();
                }).sorted(Comparator.comparing(WeekendMealCheckTeacherElement::getGrade)).toList();

        return new WeekendMealExcelListResponse(teacherLists);
    }

    @Override
    public void changeWeekendMealAllowedPeriod(boolean allowPeriod) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        postWeekendMealRepository.changeAllowedPeriodByWeekendMealIdAndAllowedPeriod(weekendMeal.getId(), allowPeriod);
    }

    @Override
    public WeekendMealCheckStatusResponse queryWeekendMealCheckStatus(int grade, int classNum) {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();

        if (weekendMeal == null) {
            throw WeekendMealNotFoundException.EXCEPTION;
        }

        boolean isCheck = queryWeekendMealCheckRepositorySpi.existWeekendMealCheck(weekendMeal.getId(), grade, classNum);
        return new WeekendMealCheckStatusResponse(isCheck);
    }

    private void weekendMealCheckSaveOrUpdate(UUID weekendMealId, UUID userId, WeekendMealCheck weekendMealCheck) {
        Optional<WeekendMealCheck> weekendMealCheckDomain = queryWeekendMealCheckRepositorySpi.queryWeekendMealCheckByWeekendMealIdAndUserId(weekendMealId, userId);

        if (weekendMealCheckDomain.isEmpty()) {
            postWeekendMealCheckRepositorySpi.postWeekendMealCheck(weekendMealCheck);
        } else {
            postWeekendMealCheckRepositorySpi.changeWeekendMealIsCheck(
                    weekendMealCheckDomain.get().getId(), weekendMealCheck.isCheck()
            );
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

        weekendMealResponseElements.sort(Comparator.comparing(WeekendMealElement::getNum));
        weekendMealNonResponseElements.sort(Comparator.comparing(WeekendMealElement::getNum));
    }

    @Override
    public WeekendMealAllowedPeriodResponse queryWeekendMealIsAllowedPeriod() {
        WeekendMeal weekendMeal = queryWeekendMealRepositorySpi.queryWeekendMeal();
        return new WeekendMealAllowedPeriodResponse(weekendMeal.isAllowedPeriod());
    }

    @Override
    public List<UUID> queryUserIdListByStatus(WeekendMealApplicationStatus status) {
        return queryWeekendMealApplyRepositorySpi.queryWeekendMealUserListByStatus(status);
    }
}
