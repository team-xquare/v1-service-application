package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.domain.stay.presentation.dto.request.ApplyStayRequest;
import io.github.v1serviceapplication.domain.stay.presentation.dto.request.SignupSettingRequest;
import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "잔류 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stay")
public class StayController {

    private final StayApi stayApi;

    @Operation(summary = "잔류 신청 API")
    @PutMapping
    public void applyStay(@RequestBody @Valid ApplyStayRequest request) {
        stayApi.applyStay(request.getStatus());
    }

    @Operation(summary = "잔류 상태 조회 API")
    @GetMapping
    public QueryStayStatusResponse queryStayStatus() {
        return stayApi.queryStayStatus();
    }

    @Operation(summary = "유저 최초 회원가입 시 초기 테이블 값 세팅 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void setDefaultStay(@RequestBody @Valid SignupSettingRequest request) {
        stayApi.setDefaultStay(request.getUserId());
    }
}
