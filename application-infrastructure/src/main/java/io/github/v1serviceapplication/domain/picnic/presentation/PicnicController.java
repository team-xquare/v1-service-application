package io.github.v1serviceapplication.domain.picnic.presentation;

import io.github.v1serviceapplication.domain.picnic.presentation.dto.request.ApplyWeekendPicnicRequest;
import io.github.v1serviceapplication.domain.picnic.presentation.dto.request.UpdatePicnicRequest;
import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicAllowTimeResponse;
import io.github.v1serviceapplication.picnic.api.dto.StudentPicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.UpdatePicnicDomainRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "주말 외출 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/picnic")
public class PicnicController {
    private final PicnicApi picnicApi;

    @Operation(summary = "주말 외출 신청 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void applyWeekendPicnic(@RequestBody @Valid ApplyWeekendPicnicRequest request) {
        ApplyWeekendPicnicDomainRequest domainRequest = ApplyWeekendPicnicDomainRequest.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .reason(request.getReason())
                .arrangement(request.getArrangement())
                .build();

        picnicApi.applyWeekendPicnic(domainRequest);
    }

    @Operation(summary = "주말 외출 신청 상세보기 API")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentPicnicDetail getStudentPicnicDetail() {
        return picnicApi.getStudentPicnicDetail();
    }

    @Operation(summary = "주말 외출 신청 수정 API")
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePicnic(@RequestBody @Valid UpdatePicnicRequest request) {
        UpdatePicnicDomainRequest domainRequest = UpdatePicnicDomainRequest.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .arrangement(request.getArrangement())
                .reason(request.getReason())
                .build();

        picnicApi.updateWeekendPicnic(domainRequest);
    }

    @Operation(summary = "주말 외출 신청 삭제 API")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWeekendPicnic() {
        picnicApi.deleteWeekendPicnic();
    }

    @Operation(summary = "주말 외출 가능 시간 조회 API")
    @GetMapping("/time")
    @ResponseStatus(HttpStatus.OK)
    public PicnicAllowTimeResponse getPicnicAllowTime() {
        return picnicApi.getPicnicAllowTime();
    }
}
