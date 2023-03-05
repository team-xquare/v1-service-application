package io.github.v1serviceapplication.infrastructure.feign.client.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserInfoResponseElement {

    private String name;
    private UUID id;
    private LocalDate birthDay;
    private Integer grade;
    private Integer classNum;
    private String profileFileName;
    private String password;
    private String accountId;
    private Integer num;
}
