package io.github.v1serviceapplication.reservation.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReserveWeekendPicnicDomainRequest {

    private String num;

    private String name;
}
