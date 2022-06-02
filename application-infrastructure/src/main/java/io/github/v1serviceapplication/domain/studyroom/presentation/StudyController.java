package io.github.v1serviceapplication.domain.studyroom.presentation;

import io.github.v1serviceapplication.domain.studyroom.presentation.dto.request.PostStudyRoomRequest;
import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomList;
import io.github.v1serviceapplication.studyroom.poststudyroom.api.PostStudyRoom;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
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

    private final QueryStudyRoom queryStudyRoom;
    private final PostStudyRoom postStudyRoom;

    @GetMapping
    public StudyRoomList queryStudyRoomList() {
        return new StudyRoomList(queryStudyRoom.queryStudyRooms());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public void postStudyRoom(@RequestBody @Valid PostStudyRoomRequest request) {
        postStudyRoom.postStudyRoom(request.getStudyRoomId());
    }

}
