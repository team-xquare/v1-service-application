package io.github.v1serviceapplication.domain.picnic.presentation;

import io.github.v1serviceapplication.domain.picnic.presentation.dto.request.ApplyWeekendPicnicRequest;
import io.github.v1serviceapplication.picnic.api.ApplyWeekendPicnic;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic")
public class PicnicController {
    private final ApplyWeekendPicnic applyWeekendPicnic;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void applyWeekendPicnic(@RequestBody @Valid ApplyWeekendPicnicRequest request) {
        ApplyWeekendPicnicDomainRequest domainRequest = ApplyWeekendPicnicDomainRequest.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        applyWeekendPicnic.applyWeekendPicnic(domainRequest);
    }
}
