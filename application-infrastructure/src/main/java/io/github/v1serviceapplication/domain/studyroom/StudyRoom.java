package io.github.v1serviceapplication.domain.studyroom;

import io.github.v1serviceapplication.domain.extensionapply.ExtensionApply;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_study_room")
@Entity
public class StudyRoom {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Size(max = 20)
    private String name;

    @OneToMany(mappedBy = "userId", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ExtensionApply> extensionApplies;
}
