package io.github.v1serviceapplication.stay.api;

import io.github.v1serviceapplication.stay.api.dto.response.*;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;

import java.util.List;
import java.util.UUID;

public interface StayApi {
    void setDefaultStay(UUID userId);
    void deleteStay(UUID userId);
    QueryStayStatusCodeResponse queryStayStatusCode();
    QueryStayStatusResponse queryStayStatus();
    void applyStay(StayStatusCode status);
    QueryAllStayStatusResponse queryAllStayStatus();
    AdminUserInfoResponse queryUserInfo(UUID userId);
    void adminChangeStayStatus(UUID userId, StayStatusCode status);
    StayApplyListResponse queryStayApplyList();
    List<UUID> queryUserIdListByStatus(StayStatusCode status);
}
