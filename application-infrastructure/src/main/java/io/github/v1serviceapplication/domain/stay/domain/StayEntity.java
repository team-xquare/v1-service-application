package io.github.v1serviceapplication.domain.stay.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "tbl_stay_apply")
@Entity
public class StayEntity extends BaseEntity {
    @NotNull
    @Column(columnDefinition = "CHAR(6)")
    private String code;
}
