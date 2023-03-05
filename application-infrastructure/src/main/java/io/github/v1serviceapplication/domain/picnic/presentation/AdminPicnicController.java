package io.github.v1serviceapplication.domain.picnic.presentation;

import io.github.v1serviceapplication.picnic.api.PicnicApi;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "어드민 주말 외출 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPicnicController {

    private final PicnicApi picnicApi;

    @Operation(summary = "주말 외출 신청한 학생 조회 API")
    @GetMapping("/picnic")
    public PicnicListResponse getPicnic() {
        return picnicApi.applyWeekendPicnics();
    }

    @Operation(summary = "주말외출 도착확인 API")
    @PatchMapping("/picnic/arrive/{picnic-id}")
    public void updateDormitoryReturnTime(@PathVariable("picnic-id") UUID picnicId) {
        picnicApi.updateDormitoryReturnTime(picnicId);
    }

    @Operation(summary = "주말외출 요청 수락 API")
    @PatchMapping("/picnic/accept/{picnic-id}")
    public void acceptPicnic(@PathVariable("picnic-id") UUID picnicId) {
        picnicApi.acceptPicnic(picnicId);
    }

    @Operation(summary = "주말외출 요청 거절 API")
    @DeleteMapping("/picnic/refuse/{picnic-id}")
    public void refusePicnic(@PathVariable("picnic-id") UUID picnicId) {
        picnicApi.refusePicnic(picnicId);
    }
}
