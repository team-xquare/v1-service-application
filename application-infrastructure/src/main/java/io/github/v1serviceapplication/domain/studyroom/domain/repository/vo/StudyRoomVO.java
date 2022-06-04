package io.github.v1serviceapplication.domain.studyroom.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StudyRoomVO {

    @QueryProjection
    public StudyRoomVO(UUID id, String name, Long applicationCount, Integer maxPeopleCount) {
        this.id = id;
        this.name = name;
        this.applicationCount = applicationCount;
        this.maxPeopleCount = maxPeopleCount;
    }

    private final UUID id;

    private final String name;

    private final Long applicationCount;

    private final Integer maxPeopleCount;

}
