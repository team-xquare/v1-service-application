package io.github.v1serviceapplication.domain.studyroom.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.UUID;

@Getter
public record StudyRoomVO(UUID id, String name, Integer applicationCount) {

    @QueryProjection
    public StudyRoomVO {
    }

}
