package io.github.v1serviceapplication.reservation.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PicnicReservationElement {
    private final UUID id;
    private final String num;
    private final String name;
}
