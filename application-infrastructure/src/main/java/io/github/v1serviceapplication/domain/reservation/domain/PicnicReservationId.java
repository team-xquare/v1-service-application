package io.github.v1serviceapplication.domain.reservation.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class PicnicReservationId implements Serializable {

    private LocalDate date;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
}
