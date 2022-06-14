package io.github.v1serviceapplication.domain.studyroom.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class StudyRoomStatusResponse {

    private final UUID studyRoomId;

}
