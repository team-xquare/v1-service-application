package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.stay.code.StayStatusCode;
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
    public void applyStay(StayStatusCode status) {
        UUID userId = userFacade.getCurrentUserId();

        StayEntity stay = StayEntity.builder()
                .userId(UUID.randomUUID())
                .code(status)
                .build();

        stayRepository.save(stay);
    }
}
