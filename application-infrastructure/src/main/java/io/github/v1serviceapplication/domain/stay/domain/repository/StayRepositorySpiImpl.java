package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.domain.stay.mapper.StayMapper;
import io.github.v1serviceapplication.error.StayNotFoundException;
import io.github.v1serviceapplication.stay.Stay;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.UserStayStatusValueResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StayRepositorySpiImpl implements StayRepositorySpi {
    private final StayRepository stayRepository;
    private final StayMapper stayMapper;

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
                        .date(stayEntity.getDate())
                        .build())
                .toList();
    }
}
