package io.github.v1serviceapplication.domain.picnic.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_picnic_apply")
@Entity
public class PicnicEntity extends BaseEntity {
    @NotNull
    private LocalTime startTime;

    private LocalTime endTime;

    @NotNull
    private String reason;

    @NotNull
    private String arrangement;
}
