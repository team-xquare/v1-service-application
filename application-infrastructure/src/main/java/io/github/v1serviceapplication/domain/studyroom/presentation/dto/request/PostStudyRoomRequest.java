package io.github.v1serviceapplication.domain.studyroom.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostStudyRoomRequest {

    @NotEmpty
    private UUID studyRoomId;

}
