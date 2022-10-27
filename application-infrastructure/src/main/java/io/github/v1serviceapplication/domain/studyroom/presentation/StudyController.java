package io.github.v1serviceapplication.domain.studyroom.presentation;

import io.github.v1serviceapplication.domain.studyroom.presentation.dto.request.PostStudyRoomRequest;
import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomList;
import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomStatusResponse;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "자습실 API")
@RequiredArgsConstructor
@RequestMapping("/study-room")
@RestController
public class StudyController {

    private final StudyRoomApi studyRoomApi;

    @Operation(summary = "자습실 목록 조회 API")
    @GetMapping
    public StudyRoomList queryStudyRoomList() {
        return new StudyRoomList(studyRoomApi.queryStudyRooms());
    }

    @Operation(summary = "자습실 신청 API")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void postStudyRoom(@RequestBody @Valid PostStudyRoomRequest request) {
        studyRoomApi.postStudyRoom(request.getStudyRoomId());
    }

    @Operation(summary = "자습실 신청 취소 API")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{studyRoomId}")
    public void cancelExtension(@PathVariable UUID studyRoomId) {
        studyRoomApi.cancelExtension(studyRoomId);
    }

    @Operation(summary = "자습실 신청 상태 조회 API")
    @GetMapping("/status")
    public StudyRoomStatusResponse queryStudyRoomStatus() {
        return new StudyRoomStatusResponse(
                studyRoomApi.queryStudyRoomStatus()
        );
    }

}
