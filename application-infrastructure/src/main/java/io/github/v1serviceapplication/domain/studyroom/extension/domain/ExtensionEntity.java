package io.github.v1serviceapplication.domain.studyroom.extension.domain;

import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import io.github.v1serviceapplication.global.entity.BaseDateEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_extension_apply")
@Entity
public class ExtensionEntity extends BaseDateEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "study_room_id")
    private StudyRoomEntity studyRoom;

    public void changeStudyRoom(StudyRoomEntity studyRoom) {
        this.studyRoom = studyRoom;
    }

}
