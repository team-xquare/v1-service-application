package io.github.v1serviceapplication.domain.stay.presentation.dto.request;

import io.github.v1serviceapplication.stay.code.StayStatusCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AdminChangeStayStatusRequest {

    @NotNull
    private StayStatusCode status;
}
