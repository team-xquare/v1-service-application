package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealEntity;
import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealRepository;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealEntity.weekendMealEntity;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealRepositoryImpl implements QueryWeekendMealRepositorySpi, PostWeekendMealRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final WeekendMealMapper weekendMealMapper;
    private final WeekendMealRepository weekendMealRepository;

    @Override
    public WeekendMeal queryWeekendMealByDate() {
        return weekendMealMapper.entityToDomain(
                jpaQueryFactory
                        .selectFrom(weekendMealEntity)
                        .where(weekendMealEntity.date.before(LocalDate.now()).not())
                        .orderBy(weekendMealEntity.date.asc())
                        .fetchFirst()
        );
    }

    @Override
    @Transactional
    public void changeWeekendMealAllowedPeriod(UUID weekendMealId, boolean allowedPeriod) {
        WeekendMealEntity entity = weekendMealRepository.findById(weekendMealId)
                .orElseThrow(()-> WeekendMealNotFoundException.EXCEPTION);

        entity.changeAllowedPeriod(allowedPeriod);
    }
}
