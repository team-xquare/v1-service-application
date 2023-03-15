package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.code.CodeElement;
import io.github.v1serviceapplication.common.UserIdFacade;
import io.github.v1serviceapplication.stay.Stay;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.*;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.exception.AlreadyExistsStayException;
import io.github.v1serviceapplication.stay.spi.PointUserFeignSpi;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import io.github.v1serviceapplication.stay.spi.StayUserFeignSpi;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@DomainService
public class StayApiImpl implements StayApi {
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
        UUID userId = userIdFacade.getCurrentUserId();
        stayRepositorySpi.applyStay(userId, status);
    }

    @Override
    public QueryAllStayStatusResponse queryAllStayStatus() {
        List<Stay> stayList = stayRepositorySpi.queryAll();
        List<UUID> userIdList = stayList.stream().map(Stay::getUserId).toList();
        Map<UUID, StayUserElement> studentList = stayUserFeignSpi.getUserInfoByUserIds(userIdList)
                .stream()
                .collect(Collectors.toMap(StayUserElement::getUserId, user -> user, (userId, user) -> user, HashMap::new));

        List<QueryAllStayStatusElement> stayStatusElements = stayList.stream()
                .map(stay -> {
                            StayUserElement user = studentList.get(stay.getUserId());

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
}
