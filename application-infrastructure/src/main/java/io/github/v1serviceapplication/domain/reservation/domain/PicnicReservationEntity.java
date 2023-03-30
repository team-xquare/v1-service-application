package io.github.v1serviceapplication.domain.reservation.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "tbl_picnic_reservation")
@Entity
public class PicnicReservationEntity extends BaseEntity {

    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isReserved;
}
