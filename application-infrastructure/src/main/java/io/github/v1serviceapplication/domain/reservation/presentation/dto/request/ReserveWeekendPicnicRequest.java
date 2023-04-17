package io.github.v1serviceapplication.domain.reservation.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReserveWeekendPicnicRequest {

    @NotNull
    private boolean reserved;
}
