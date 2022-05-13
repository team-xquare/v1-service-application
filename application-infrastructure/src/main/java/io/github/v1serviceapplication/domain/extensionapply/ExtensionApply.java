package io.github.v1serviceapplication.domain.extensionapply;

import io.github.v1serviceapplication.domain.studyroom.StudyRoom;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "tbl_extension_apply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExtensionApply {
    @Id
    private UUID userId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "study_room_id")
    private StudyRoom studyRoomId;
}
