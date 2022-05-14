package io.github.v1serviceapplication.domain.extensionapply;

import io.github.v1serviceapplication.domain.studyroom.StudyRoom;
import io.github.v1serviceapplication.global.entity.BaseUUIDEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_extension_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExtensionApply extends BaseUUIDEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoom;
}
