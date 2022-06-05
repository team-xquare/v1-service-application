package io.github.v1serviceapplication.domain.stay.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import io.github.v1serviceapplication.domain.stay.code.StayStatusCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Table(name = "tbl_stay_apply")
@Entity
public class StayEntity extends BaseEntity {
    @NotNull
    @Column(columnDefinition = "CHAR(6)")
    @Convert(converter = StayStatusCode.StayStatusCodeConverter.class)
    private StayStatusCode code;
}
