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

}
