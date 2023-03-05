package io.github.v1serviceapplication.infrastructure.excel.service;

import io.github.v1serviceapplication.domain.stay.domain.repository.StayRepository;
import io.github.v1serviceapplication.domain.stay.mapper.StayMapper;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayApplyListResponse;
import io.github.v1serviceapplication.infrastructure.excel.presentation.dto.StayStatus;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StayExcelService {

    private final StayRepository stayRepository;
    private final StayMapper stayMapper;
    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi;

    public StayApplyListResponse getStayApplyList() {
        return StayApplyListResponse.builder()
                .students(getStayStatusList())
                .build();
    }

    private List<StayStatus> getStayStatusList() {

        List<StayStatus> stayList = stayRepository.findAll()
                .stream()
                .map(stayMapper::mapToStayStatus)
                .toList();

        return studyRoomUserFeignSpi.queryAllUser()
                .stream()
                .map(user -> {
                    String stay = stayList.stream()
                            .filter(stayStatus -> user.getUserId().equals(stayStatus.getUserId()))
                            .map(StayStatus::getStay)
                            .findFirst()
                            .orElseGet(() -> "금요귀가");

                    return StayStatus.builder()
                            .userId(user.getUserId())
                            .name(user.getStudentName())
                            .num(getStudentNum(user.getGrade(), user.getClassNum(), user.getNum()))
                            .stay(stay)
                            .build();

                })
                .collect(Collectors.toList());
    }

    private String getStudentNum(int grade, int classNum, int num) {
        return String.valueOf(grade) + classNum + String.format("%02d", num);
    }
}