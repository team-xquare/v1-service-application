package io.github.v1serviceapplication.domain.studyroom;

import io.github.v1serviceapplication.domain.extensionapply.ExtensionApply;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tbl_study_room")
@Entity
public class StudyRoom {
    @Id
    private UUID id;

    @NotNull
    @Size(max = 20)
    private String name;

    @OneToMany(mappedBy = "userId", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExtensionApply> extensionApplies;
}
