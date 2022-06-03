package io.github.v1serviceapplication.domain.stay.presentation.dto.request;

import io.github.v1serviceapplication.domain.stay.code.StayStatusCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ApplyStayRequest {

    @NotNull
    private StayStatusCode status;
}
