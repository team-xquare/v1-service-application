package io.github.v1serviceapplication.domain.stay.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.stay.domain.StayEntity;
import io.github.v1serviceapplication.error.StayNotFoundException;
import io.github.v1serviceapplication.global.facade.UserFacade;
import io.github.v1serviceapplication.stay.api.dto.response.QueryStayStatusResponse;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.stay.domain.QStayEntity.stayEntity;

@Repository
@RequiredArgsConstructor
public class StayRepositorySpiImpl implements StayRepositorySpi {
    private final StayRepository stayRepository;
    private final JPAQueryFactory queryFactory;
    private final UserFacade userFacade;

    @Override
    @Transactional
    public void applyStay(StayStatusCode status) {
        UUID userId = UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398");

        StayEntity stay = queryStayByUserAndWeekYear(userId, getCurrentWeekYear());

        if (stay != null) {
            stay.changeCode(status);
        } else {
            stayRepository.save(
                    StayEntity.builder()
                            .userId(userId)
                            .code(status)
                            .build()
            );
        }

    }

    @Override
    public QueryStayStatusResponse queryStayStatus(UUID userId) {
        StayEntity stay = queryStayByUserAndWeekYear(
                UUID.fromString("19d1e9b7-0d51-4405-bd1d-042cab403398"), getCurrentWeekYear()
        );

        if(stay == null) {
            throw StayNotFoundException.EXCEPTION;
        }

        return QueryStayStatusResponse.builder()
                .status(stay.getCodeName(stay.getCode()))
                .build();
    }

    @Override
    public void setDefaultStay(UUID userId) {
        StayEntity stay = StayEntity.builder()
                .userId(userId)
                .code(StayStatusCode.STAY)
                .build();

        stayRepository.save(stay);
    }

    private StayEntity queryStayByUserAndWeekYear(UUID userId, int weekYear) {
        return queryFactory
                .selectFrom(stayEntity)
                .where(
                        stayEntity.userId.eq(userId)
                                .and(
                                        stayEntity.date.week().eq(weekYear)
                                )
                )
                .fetchFirst();
    }

    private int getCurrentWeekYear() {
        LocalDate date = LocalDate.now();

        return date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }
}
