package io.github.v1serviceapplication.domain.picnic.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ApplyWeekendPicnicRequest {
    @NotNull
    private LocalTime start_time;

    private LocalTime end_time;

    @NotEmpty
    private String reason;

    @NotEmpty
    private String arrangement;
}
