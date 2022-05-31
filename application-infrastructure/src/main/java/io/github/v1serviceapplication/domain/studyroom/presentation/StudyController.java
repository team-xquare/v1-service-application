package io.github.v1serviceapplication.domain.studyroom.presentation;

import io.github.v1serviceapplication.domain.studyroom.presentation.dto.response.StudyRoomList;
import io.github.v1serviceapplication.studyroom.querystudyroom.api.QueryStudyRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/study-room")
public class StudyController {

    private final QueryStudyRoom queryStudyRoom;

    @GetMapping
    public StudyRoomList queryStudyRoomList() {
        return new StudyRoomList(queryStudyRoom.queryStudyRoom());
    }

}
