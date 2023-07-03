package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.code.CodeElement;
import io.github.v1serviceapplication.error.UserNotFoundException;
import io.github.v1serviceapplication.stay.exception.CanNotStayApplyException;
import io.github.v1serviceapplication.user.UserIdFacade;
import io.github.v1serviceapplication.stay.Stay;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.AdminUserInfoResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryAllStayStatusElement;
import io.github.v1serviceapplication.stay.api.dto.response.QueryAllStayStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.StayApplyListResponse;
import io.github.v1serviceapplication.stay.api.dto.response.StayUserElement;
import io.github.v1serviceapplication.stay.api.dto.response.UserPointStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.UserStayStatusValueResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.exception.AlreadyExistsStayException;
import io.github.v1serviceapplication.stay.spi.PointUserFeignSpi;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import io.github.v1serviceapplication.stay.spi.StayUserFeignSpi;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class StayApiImpl implements StayApi {
    private static final Logger LOGGER = Logger.getLogger(StayApiImpl.class.getName());
    private final StayRepositorySpi stayRepositorySpi;
    private final UserIdFacade userIdFacade;
    private final StayUserFeignSpi stayUserFeignSpi;
    private final PointUserFeignSpi pointUserFeignSpi;

    @Override
    public void setDefaultStay(UUID userId) {
        if (stayRepositorySpi.existsByUserId(userId)) {
            throw AlreadyExistsStayException.EXCEPTION;
        }
        stayRepositorySpi.applyStay(userId, StayStatusCode.STAY);
    }

    @Override
    public void deleteStay(UUID userId) {
        stayRepositorySpi.deleteStay(userId);
    }

    @Override
    public QueryStayStatusCodeResponse queryStayStatusCode() {
        return QueryStayStatusCodeResponse.builder()
                .codes(Arrays.stream(StayStatusCode.values())
                        .map(stayStatus -> CodeElement.builder()
                                .name(stayStatus.name())
                                .value(stayStatus.getValue())
                                .build()
                        ).collect(Collectors.toList())
                ).build();
    }

    @Override
    public QueryStayStatusResponse queryStayStatus() {
        return stayRepositorySpi.queryStayStatus(userIdFacade.getCurrentUserId());
    }

    @Override
    public void applyStay(StayStatusCode status) {
        validCheckApplyStayTime();
        UUID userId = userIdFacade.getCurrentUserId();
        stayRepositorySpi.applyStay(userId, status);
    }

    private void validCheckApplyStayTime() {
        boolean validRequestWeek = !LocalDate.now().getDayOfWeek().equals(DayOfWeek.THURSDAY);
        boolean validRequestStartTime  = LocalTime.now().isAfter(LocalTime.parse("23:00:00.00"));
        boolean validRequestEndTime = LocalTime.now().isBefore(LocalTime.parse("20:30:00"));

        if(validRequestWeek || validRequestStartTime || validRequestEndTime) {
            throw CanNotStayApplyException.EXCEPTION;
        }
    }

    @Override
    public QueryAllStayStatusResponse queryAllStayStatus() {
        List<Stay> stayList = stayRepositorySpi.queryAll();
        Map<UUID, StayUserElement> studentList = stayUserFeignSpi.getStudent()
                .stream()
                .collect(Collectors.toMap(StayUserElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<QueryAllStayStatusElement> stayStatusElements = stayList.stream()
                .map(stay -> {
                            StayUserElement user = studentList.get(stay.getUserId());

                            if(user == null) {
                                LOGGER.info(String.valueOf(stay.getUserId()));
                                throw UserNotFoundException.EXCEPTION;
                            }
                            return QueryAllStayStatusElement.builder()
                                    .id(stay.getUserId())
                                    .num(user.getNum())
                                    .name(user.getName())
                                    .code(stay.getCode())
                                    .build();
                        }
                )
                .sorted(Comparator.comparing(QueryAllStayStatusElement::getNum))
                .toList();

        return new QueryAllStayStatusResponse(stayStatusElements);
    }

    @Override
    public AdminUserInfoResponse queryUserInfo(UUID userId) {
        StayUserElement user = stayUserFeignSpi.getUserInfo(userId);
        UserStayStatusValueResponse stay = stayRepositorySpi.queryStayStatusValue(userId);
        UserPointStatusResponse point = pointUserFeignSpi.getUserPointStatus(userId);

        return AdminUserInfoResponse.builder()
                .username(user.getName())
                .num(user.getNum())
                .goodPoint(point.getGoodPoint())
                .badPoint(point.getBadPoint())
                .code(stay.getStatus())
                .build();
    }

    @Override
    public void adminChangeStayStatus(UUID userId, StayStatusCode status) {
        stayRepositorySpi.changeStayStatus(userId, status);
    }

    @Override
    public StayApplyListResponse queryStayApplyList() {
        return stayRepositorySpi.queryStayApplyList();
    }

    @Override
    public List<UUID> queryUserIdListByStatus(StayStatusCode status) {
        return stayRepositorySpi.queryStayUserListByStatus(status);
    }
}
