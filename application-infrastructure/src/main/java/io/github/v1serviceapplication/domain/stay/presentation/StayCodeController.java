package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.stay.api.StayApi;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stay/codes")
public class StayCodeController {
    private final StayApi stayApi;

    @GetMapping("/status")
    public QueryStayStatusCodeResponse queryStayStatusCode() {
        return stayApi.queryStayStatusCode();
    }

}
