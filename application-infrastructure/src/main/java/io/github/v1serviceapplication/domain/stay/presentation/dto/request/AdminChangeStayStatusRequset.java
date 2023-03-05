package io.github.v1serviceapplication.domain.stay.presentation.dto.request;

import io.github.v1serviceapplication.stay.code.StayStatusCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AdminChangeStayStatusRequset {

    @NotNull
    private StayStatusCode status;
}
