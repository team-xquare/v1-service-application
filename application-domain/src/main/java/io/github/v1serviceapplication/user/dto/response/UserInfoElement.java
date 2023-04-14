package io.github.v1serviceapplication.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserInfoElement {

    private final UUID userId;
    private final String num;
    private final String name;
}
