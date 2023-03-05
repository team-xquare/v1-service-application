package io.github.v1serviceapplication.domain.stay.mapper;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayStatus;
import io.github.v1serviceapplication.studyroom.api.dto.response.StudentElement;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class StayMapperImpl implements StayMapper {

    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi;

    @Override
    public StayStatus mapToStayStatus(StayEntity stayStatus) {
        return StayStatus.builder()
                .userId(stayStatus.getUserId())
                .name(queryNameByUserId(stayStatus.getUserId()))
                .num(queryNumByUserId(stayStatus.getUserId()))
                .stay(stayStatus.getCode().getValue())
                .build();
    }

    private String queryNameByUserId(UUID userId) {
        List<StudentElement> userInfoResponse = studyRoomUserFeignSpi.queryUserInfoByUserId(List.of(userId));
        List<String> nameList = userInfoResponse
                .stream()
                .map(StudentElement::getStudentName)
                .toList();

        return nameList.get(0);
    }

    private String queryNumByUserId(UUID userId) {
        List<StudentElement> userInfoResponse = studyRoomUserFeignSpi.queryUserInfoByUserId(List.of(userId));
        List<Integer> gradeList = userInfoResponse
                .stream()
                .map(StudentElement::getGrade)
                .toList();
        List<Integer> classNumList = userInfoResponse
                .stream()
                .map(StudentElement::getClassNum)
                .toList();
        List<Integer> numList = userInfoResponse
                .stream()
                .map(StudentElement::getNum)
                .toList();

        return gradeList.get(0).toString() + classNumList.get(0).toString() + numList.get(0).toString();
    }
}
