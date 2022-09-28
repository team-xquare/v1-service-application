package io.github.v1serviceapplication.domain.stay.presentation.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class SignupSettingRequest {
    @NotNull
    private UUID userId;
}
