package io.github.v1serviceapplication.reservation.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PicnicReservationListResponse {
    private List<PicnicReservationElement> picnicReservationElementList;
}
