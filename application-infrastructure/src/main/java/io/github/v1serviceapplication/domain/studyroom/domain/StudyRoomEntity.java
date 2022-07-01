package io.github.v1serviceapplication.domain.studyroom.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tbl_study_room")
@Entity
public class StudyRoomEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Size(max = 20)
    private String name;

    @NotNull
    private Integer maxPeopleCount;

    @NotNull
    private Integer floor;

    @Builder
    public StudyRoomEntity(String name, Integer maxPeopleCount) {
        this.name = name;
        this.maxPeopleCount = maxPeopleCount;
    }
}
