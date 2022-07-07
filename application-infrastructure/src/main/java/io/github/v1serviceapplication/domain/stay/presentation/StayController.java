package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.domain.stay.presentation.dto.request.ApplyStayRequest;
import io.github.v1serviceapplication.domain.stay.presentation.dto.request.SetDefaultStayRequest;
import io.github.v1serviceapplication.stay.api.ApplyStay;
import io.github.v1serviceapplication.stay.api.QueryStayStatus;
import io.github.v1serviceapplication.stay.api.SetDefaultStay;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stay")
public class StayController {

    private final SetDefaultStay setDefaultStay;
    private final ApplyStay applyStay;
    private final QueryStayStatus queryStayStatus;

    @PutMapping
    public void applyStay(@RequestBody @Valid ApplyStayRequest request) {
        applyStay.applyStay(request.getStatus());
    }

    @GetMapping
    public QueryStayStatusResponse queryStayStatus() {
        return queryStayStatus.queryStayStatus();
    }

    @PostMapping("/signup")
    public void setDefaultStay(@RequestBody @Valid SetDefaultStayRequest request) {
        setDefaultStay.setDefaultStay(request.getUserId());
    }
}
