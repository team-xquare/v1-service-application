package io.github.v1serviceapplication.domain.reservation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReserveWeekendPicnicRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String num;
}
