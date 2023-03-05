package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryAllStayStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민 잔류 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminStayController {

    private final StayApi stayApi;

    @Operation(summary = "어드민 잔류 조회 API")
    @GetMapping("/stay")
    public QueryAllStayStatusResponse getAllStayStatus() {
        return stayApi.queryAllStayStatus();
    }
}
