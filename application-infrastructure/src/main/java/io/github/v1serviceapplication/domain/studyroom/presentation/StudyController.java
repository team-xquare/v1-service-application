package io.github.v1serviceapplication.domain.studyroom.presentation;

import io.github.v1serviceapplication.domain.studyroom.presentation.dto.request.PostStudyRoomRequest;
import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomList;
import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomStatusResponse;
import io.github.v1serviceapplication.global.facade.UserFacade;
import io.github.v1serviceapplication.studyroom.api.StudyRoomApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/study-room")
@RestController
public class StudyController {

    private final StudyRoomApi studyRoomApi;
    private final UserFacade userFacade;

    @GetMapping
    public StudyRoomList queryStudyRoomList() {
        return new StudyRoomList(studyRoomApi.queryStudyRooms());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postStudyRoom(@RequestBody @Valid PostStudyRoomRequest request) {
        studyRoomApi.postStudyRoom(request.getStudyRoomId(), userFacade.getCurrentUserId());
    }

    @GetMapping("/status")
    public StudyRoomStatusResponse queryStudyRoomStatus() {
        return new StudyRoomStatusResponse(
                studyRoomApi.queryStudyRoomStatus(userFacade.getCurrentUserId())
        );
    }

}
