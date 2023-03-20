package io.github.v1serviceapplication.stay.api;

import io.github.v1serviceapplication.stay.api.dto.response.*;
import io.github.v1serviceapplication.stay.code.StayStatusCode;

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
}
