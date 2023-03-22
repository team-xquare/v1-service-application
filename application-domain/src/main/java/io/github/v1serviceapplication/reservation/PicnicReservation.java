package io.github.v1serviceapplication.reservation;

import io.github.v1serviceapplication.annotation.Aggregate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Aggregate
@Getter
@Builder
public class PicnicReservation {
    private final UUID id;
    private final UUID userId;
    private final LocalDate date;
    private final Boolean isReserved;
}
