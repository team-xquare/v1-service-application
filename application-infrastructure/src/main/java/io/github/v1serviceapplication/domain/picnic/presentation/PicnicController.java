package io.github.v1serviceapplication.domain.picnic.presentation;

import io.github.v1serviceapplication.domain.picnic.presentation.dto.request.ApplyWeekendPicnicRequest;
import io.github.v1serviceapplication.global.facade.UserFacade;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic")
public class PicnicController {
    private final PicnicApi picnicApi;
    private final UserFacade userFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void applyWeekendPicnic(@RequestBody @Valid ApplyWeekendPicnicRequest request) {
        ApplyWeekendPicnicDomainRequest domainRequest = ApplyWeekendPicnicDomainRequest.builder()
                .userId(userFacade.getCurrentUserId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        picnicApi.applyWeekendPicnic(domainRequest);
    }
}
