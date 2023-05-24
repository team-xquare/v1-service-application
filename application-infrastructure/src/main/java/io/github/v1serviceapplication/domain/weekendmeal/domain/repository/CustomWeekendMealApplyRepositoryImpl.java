package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealApplyMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApplicationStatus;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealApplyNotFoundException;
import io.github.v1serviceapplication.weekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import io.github.v1serviceapplication.weekendmeal.spi.QueryWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealApplyEntity.weekendMealApplyEntity;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealApplyRepositoryImpl implements PostWeekendMealApplyRepositorySpi, QueryWeekendMealApplyRepositorySpi {

    private final WeekendMealApplyRepository weekendMealApplyRepository;
    private final WeekendMealApplyMapper weekendMealApplyMapper;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional
    public void updateWeekendMealApply(UUID userId, UUID weekendMealId, WeekendMealApplicationStatus status) {
        WeekendMealApplyEntity weekendMealApply = weekendMealApplyRepository.findByUserIdAndWeekendMealId(userId, weekendMealId)
                .orElseThrow(() -> WeekendMealApplyNotFoundException.EXCEPTION);

        weekendMealApply.updateApplied(status);
    }

    @Override
    public WeekendMealApplicationStatus queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId) {
        return weekendMealApplyRepository.findByUserIdAndWeekendMealId(userId, weekendMealId)
                .map(WeekendMealApplyEntity::getStatus)
                .orElse(WeekendMealApplicationStatus.NONRESPONSE);
    }

    @Override
    public List<UUID> queryWeekendMealUserList() {
        return jpaQueryFactory
                .select(weekendMealApplyEntity.userId)
                .from(weekendMealApplyEntity)
                .fetch();
    }

    @Override
    public List<WeekendMealApply> findAll() {
        return jpaQueryFactory
                .selectFrom(weekendMealApplyEntity)
                .where(
                        weekendMealApplyEntity.status.eq(WeekendMealApplicationStatus.APPLY),
                        weekendMealApplyEntity.status.eq(WeekendMealApplicationStatus.NOTAPPLY)
                )
                .fetch()
                .stream()
                .map(weekendMealApplyMapper::entityToDomain)
                .toList();
    }
}
