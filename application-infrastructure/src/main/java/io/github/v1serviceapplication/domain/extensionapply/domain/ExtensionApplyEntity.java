package io.github.v1serviceapplication.domain.extensionapply.domain;

import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import io.github.v1serviceapplication.global.entity.BaseUUIDEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_extension_apply")
@Entity
public class ExtensionApplyEntity extends BaseUUIDEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_room_id")
    private StudyRoomEntity studyRoom;
}
