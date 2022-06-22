package io.github.v1serviceapplication.domain.stay.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class SetDefaultStayRequest {
    @NotNull
    private UUID userId;
}
