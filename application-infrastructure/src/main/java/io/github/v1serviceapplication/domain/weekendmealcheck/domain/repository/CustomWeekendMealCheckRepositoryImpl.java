package io.github.v1serviceapplication.domain.weekendmealcheck.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.domain.weekendmealcheck.mapper.WeekendMealCheckMapper;
import io.github.v1serviceapplication.weekendmeakcheck.WeekendMealCheck;
import io.github.v1serviceapplication.weekendmeakcheck.exception.WeekendMealCheckNotFoundException;
import io.github.v1serviceapplication.weekendmeakcheck.spi.PostWeekendMealCheckRepositorySpi;
import io.github.v1serviceapplication.weekendmeakcheck.spi.QueryWeekendMealCheckRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealEntity.weekendMealEntity;
import static io.github.v1serviceapplication.domain.weekendmealcheck.domain.QWeekendMealCheckEntity.weekendMealCheckEntity;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealCheckRepositoryImpl implements PostWeekendMealCheckRepositorySpi, QueryWeekendMealCheckRepositorySpi {

    private final WeekendMealCheckRepository weekendMealCheckRepository;
    private final WeekendMealCheckMapper weekendMealCheckMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public void postWeekendMealCheck(WeekendMealCheck weekendMealCheck) {
        weekendMealCheckRepository.save(weekendMealCheckMapper.domainToEntity(weekendMealCheck));
    }

    @Override
    @Transactional
    public void changeWeekendMealIsCheck(UUID weekendMealId, boolean isCheck) {
        WeekendMealCheckEntity weekendMealCheck = weekendMealCheckRepository.findById(weekendMealId)
                .orElseThrow(()-> WeekendMealCheckNotFoundException.EXCEPTION);
        weekendMealCheck.changeIsCheck(isCheck);
    }

    @Override
    public boolean existsWeekendMealCheck(UUID weekendMealId, UUID userId) {
       return queryFactory
                .selectFrom(weekendMealCheckEntity)
                .join(weekendMealEntity)
                .on(weekendMealCheckEntity.weekendMeal.id.eq(weekendMealEntity.id))
                .where(
                        weekendMealCheckEntity.userId.eq(userId),
                        weekendMealCheckEntity.weekendMeal.id.eq(weekendMealId)
                )
                .fetchOne() == null;
    }

    @Override
    public WeekendMealCheck queryWeekendMealByweekendMealIdAndUserId(UUID weekendMealId, UUID userId) {
        WeekendMealCheckEntity entity = queryFactory
                .selectFrom(weekendMealCheckEntity)
                .join(weekendMealEntity)
                .on(weekendMealCheckEntity.weekendMeal.id.eq(weekendMealEntity.id))
                .where(
                        weekendMealCheckEntity.userId.eq(userId),
                        weekendMealCheckEntity.weekendMeal.id.eq(weekendMealId)
                )
                .fetchOne();

        return weekendMealCheckMapper.entityToDomain(entity);
    }
}
