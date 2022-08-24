package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMeal;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealEntity.weekendMealEntity;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealRepositoryImpl implements QueryWeekendMealRepositorySpi {

    private final JPAQueryFactory jpaQueryFactory;
    private final WeekendMealMapper weekendMealMapper;

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

}
