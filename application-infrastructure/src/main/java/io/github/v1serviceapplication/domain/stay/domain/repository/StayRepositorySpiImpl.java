package io.github.v1serviceapplication.domain.stay.domain.repository;

import io.github.v1serviceapplication.error.StayNotFoundException;
import io.github.v1serviceapplication.stay.Stay;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
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
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398");

        StayEntity stay = StayEntity.builder()
                .userId(userId)
                .code(status)
                .build();

        stayRepository.save(stay);
    }

    @Override
    public QueryStayStatusResponse queryStayStatus(UUID userId) {
        StayEntity stay = stayRepository.findByUserId(UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398"))
                .orElseThrow(() -> StayNotFoundException.EXCEPTION);

        return QueryStayStatusResponse.builder()
                .status(stay.getCodeName(stay.getCode()))
                .build();
    }
}
