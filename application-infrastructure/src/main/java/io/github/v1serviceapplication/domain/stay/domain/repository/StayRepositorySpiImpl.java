package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.error.StayNotFoundException;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StayRepositorySpiImpl implements StayRepositorySpi {
    private final StayRepository stayRepository;

    @Override
    @Transactional
    public void applyStay(StayStatusCode status, UUID userId) {
        saveOrUpdate(userId, status);
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
    public void setDefaultStay(UUID userId) {
        saveOrUpdate(userId, StayStatusCode.STAY);
    }

    @Override
    public void deleteStay(UUID userId) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElseThrow(() -> StayNotFoundException.EXCEPTION);

        stayRepository.delete(stay);
    }

    private void saveOrUpdate(UUID userId, StayStatusCode statusCode) {
        StayEntity stay = stayRepository.findByUserId(userId)
                .orElse(null);

        if (stay != null) {
            stay.changeCode(statusCode);
        } else {
            stayRepository.save(
                    StayEntity.builder()
                            .userId(userId)
                            .code(statusCode)
                            .build()
            );
        }
    }

}
