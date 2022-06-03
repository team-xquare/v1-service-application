package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.domain.stay.code.StayStatusCode;
import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.global.facade.UserFacade;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StayRepositorySpiImpl implements StayRepositorySpi {
    private final StayRepository stayRepository;
    private final UserFacade userFacade;

    @Override
    public void applyStay(String status) {
        StayStatusCode statusCode = StayStatusCode.find(status);

        UUID userId = userFacade.getCurrentUserId();

        StayEntity stay = StayEntity.builder()
                .userId(userId)
                .code(statusCode)
                .build();

        stayRepository.save(stay);
    }
}
