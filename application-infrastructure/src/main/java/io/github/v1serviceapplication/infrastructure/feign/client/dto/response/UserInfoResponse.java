package io.github.v1serviceapplication.infrastructure.feign.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoResponse {

    private List<UserInfoResponseElement> users;

}
