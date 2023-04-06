package io.github.v1serviceapplication.reservation.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PicnicReservationElement {
    private final String num;
    private final String name;
    private final boolean reserved;
}
