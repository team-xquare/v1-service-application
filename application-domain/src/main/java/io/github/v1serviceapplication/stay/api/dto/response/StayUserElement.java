package io.github.v1serviceapplication.stay.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class StayUserElement {
    private final UUID userId;
    private final String num;
    private final String name;
}
