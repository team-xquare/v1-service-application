package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "잔류 코드 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/stay/codes")
public class StayCodeController {
    private final StayApi stayApi;

    @Operation(summary = "잔류 코드 조회 API")
    @GetMapping("/status")
    public QueryStayStatusCodeResponse queryStayStatusCode() {
        return stayApi.queryStayStatusCode();
    }

}
