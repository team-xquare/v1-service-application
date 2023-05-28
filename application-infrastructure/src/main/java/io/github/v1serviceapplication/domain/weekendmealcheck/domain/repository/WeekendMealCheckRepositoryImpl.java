package io.github.v1serviceapplication.domain.weekendmealcheck.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmealcheck.domain.WeekendMealCheckEntity;
import io.github.v1serviceapplication.domain.weekendmealcheck.mapper.WeekendMealCheckMapper;
import io.github.v1serviceapplication.weekendmealcheck.WeekendMealCheck;
import io.github.v1serviceapplication.weekendmealcheck.spi.PostWeekendMealCheckSpi;
import io.github.v1serviceapplication.weekendmealcheck.spi.QueryWeekendMealCheckRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealEntity.weekendMealEntity;
import static io.github.v1serviceapplication.domain.weekendmealcheck.domain.QWeekendMealCheckEntity.weekendMealCheckEntity;

@RequiredArgsConstructor
@Repository
public class WeekendMealCheckRepositoryImpl implements QueryWeekendMealCheckRepositorySpi, PostWeekendMealCheckSpi {

    private final JPAQueryFactory jpaQueryFactory;
    private final WeekendMealCheckMapper weekendMealCheckMapper;
    private final WeekendMealCheckRepository weekendMealCheckRepository;
    @Override
    public Boolean queryWeekendMealCheckByTeacherId(UUID teacherId, UUID weekendMealId) {
        return jpaQueryFactory
                .selectFrom(weekendMealCheckEntity)
                .join(weekendMealCheckEntity.weekendMeal, weekendMealEntity)
                .on(weekendMealCheckEntity.weekendMeal.id.eq(weekendMealId))
                .where(weekendMealCheckEntity.userId.eq(teacherId))
                .fetchOne() != null;
    }

    @Override
    public void saveWeekendMealCheck(WeekendMealCheck weekendMealCheck) {
        WeekendMealCheckEntity entity = weekendMealCheckMapper.domainToEntity(weekendMealCheck);

        weekendMealCheckRepository.save(
                entity
        );
    }
}
