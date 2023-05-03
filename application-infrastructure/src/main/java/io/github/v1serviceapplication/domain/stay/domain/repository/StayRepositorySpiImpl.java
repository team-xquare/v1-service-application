package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.domain.stay.mapper.StayMapper;
import io.github.v1serviceapplication.stay.api.dto.response.StayApplyListResponse;
import io.github.v1serviceapplication.stay.api.dto.response.StayStatus;
import io.github.v1serviceapplication.error.StayNotFoundException;
import io.github.v1serviceapplication.error.UserNotFoundException;
import io.github.v1serviceapplication.stay.Stay;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.UserStayStatusValueResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StayRepositorySpiImpl implements StayRepositorySpi {
    private final StayRepository stayRepository;
    private final StayMapper stayMapper;
    private final StudyRoomUserFeignSpi studyRoomUserFeignSpi;

    @Override
    @Transactional
    public void applyStay(UUID userId, StayStatusCode stayStatusCode) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElse(null);

        if (stay != null) {
            stay.changeCode(stayStatusCode);
        } else {
            stayRepository.save(
                    StayEntity.builder()
                            .userId(userId)
                            .code(stayStatusCode)
                            .build()
            );
        }
    }

    @Override
    public QueryStayStatusResponse queryStayStatus(UUID userId) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElseThrow(() -> StayNotFoundException.EXCEPTION);

        return QueryStayStatusResponse.builder()
                .status(stay.getCodeName(stay.getCode()))
                .build();
    }

    @Override
    public UserStayStatusValueResponse queryStayStatusValue(UUID userId) {
        StayEntity stayEntity = stayRepository.findByUserId(userId)
                .orElseThrow(() -> StayNotFoundException.EXCEPTION);
        return new UserStayStatusValueResponse(
                stayEntity.getCode().getValue()
        );
    }

    @Override
    public void deleteStay(UUID userId) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElseThrow(() -> StayNotFoundException.EXCEPTION);

        stayRepository.delete(stay);
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return stayRepository.findByUserId(userId)
                .isPresent();
    }

    @Override
    public List<Stay> queryAll() {
        return stayRepository.findAll()
                .stream()
                .map(stayEntity -> Stay.builder()
                        .id(stayEntity.getId())
                        .userId(stayEntity.getUserId())
                        .code(stayEntity.getCode().getValue())
                        .createDate(stayEntity.getCreateDate())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void changeStayStatus(UUID userId, StayStatusCode stayStatusCode) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        stay.changeCode(stayStatusCode);
    }

    @Override
    public StayApplyListResponse queryStayApplyList() {
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
    