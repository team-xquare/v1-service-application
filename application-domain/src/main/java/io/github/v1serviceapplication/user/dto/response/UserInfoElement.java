package io.github.v1serviceapplication.user.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserInfoElement {

    private final UUID userId;
    private final String num;
    private final String name;
}
