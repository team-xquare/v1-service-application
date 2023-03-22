package io.github.v1serviceapplication.domain.reservation.domain;

import io.github.v1serviceapplication.global.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    public void updateReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}
