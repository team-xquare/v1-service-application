package io.github.v1serviceapplication.domain.reservation.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tbl_picnic_reservation")
@Entity
public class PicnicReservationEntity implements Serializable {

    @EmbeddedId
    private PicnicReservationId id;

    @NotNull
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isReserved;
}
