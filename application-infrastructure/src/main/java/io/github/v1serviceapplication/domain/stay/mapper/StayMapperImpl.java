package io.github.v1serviceapplication.domain.stay.mapper;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayStatus;
import io.github.v1serviceapplication.infrastructure.feign.client.UserClient;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponse;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponseElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class StayMapperImpl implements StayMapper {

    private final UserClient userClient;

    @Override
    public StayStatus mapToStayStatus(StayEntity stayStatus) {
        return StayStatus.builder()
                .id(stayStatus.getId())
                .name(queryNameByUserId(stayStatus.getUserId()))
                .num(queryNumByUserId(stayStatus.getUserId()))
                .stay(stayStatus.getCode().getValue())
                .build();
    }

    private String queryNameByUserId(UUID userId) {
        UserInfoResponse userInfoResponse = userClient.queryUserInfoByUserId(List.of(userId));
        List<String> nameList = userInfoResponse.getUsers()
                .stream()
                .map(UserInfoResponseElement::getName)
                .toList();

        return nameList.get(0);
    }

    private String queryNumByUserId(UUID userId) {
        UserInfoResponse userInfoResponse = userClient.queryUserInfoByUserId(List.of(userId));
        List<Integer> gradeList = userInfoResponse.getUsers()
                .stream()
                .map(UserInfoResponseElement::getGrade)
                .toList();
        List<Integer> classNumList = userInfoResponse.getUsers()
                .stream()
                .map(UserInfoResponseElement::getClassNum)
                .toList();
        List<Integer> numList = userInfoResponse.getUsers()
                .stream()
                .map(UserInfoResponseElement::getNum)
                .toList();

        return gradeList.get(0).toString() + classNumList.get(0).toString() + numList.get(0).toString();
    }
}
