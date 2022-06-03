package io.github.v1serviceapplication.domain.stay.presentation;

import io.github.v1serviceapplication.domain.stay.presentation.dto.request.ApplyStayRequest;
import io.github.v1serviceapplication.stay.api.ApplyStay;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stay")
public class StayController {
    private final ApplyStay applyStay;

    @PutMapping
    public void applyStay(@RequestBody @Valid ApplyStayRequest request) {
        applyStay.applyStay(request.getStatus().getCode());
    }
}
