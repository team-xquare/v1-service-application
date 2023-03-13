package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealApplyMapper;
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
    public boolean currentWeekendMealApplyExist(UUID userId, UUID weekendMealId) {
        return jpaQueryFactory
                .select(weekendMealApplyEntity)
                .from(weekendMealApplyEntity)
                .where(
                        weekendMealApplyEntity.userId.eq(userId)
                                .and(
                                        weekendMealApplyEntity.weekendMeal.id.eq(weekendMealId)
                                )
                )
                .fetchFirst() != null;
    }

    @Override
    public void saveWeekendMealApply(WeekendMealApply weekendMealApply) {
        weekendMealApplyRepository.save(
                weekendMealApplyMapper.domainToEntity(weekendMealApply)
        );
    }

    @Override
    @Transactional
    public void updateWeekendMealApply(UUID userId, UUID weekendMealId, Boolean apply) {
        WeekendMealApplyEntity weekendMealApply = weekendMealApplyRepository.findByUserIdAndWeekendMealId(userId, weekendMealId)
                .orElseThrow(() -> WeekendMealApplyNotFoundException.EXCEPTION);

        weekendMealApply.updateApplied(apply);
    }

    @Override
    public boolean queryWeekendMealApplyAppliedByUserIdAndWeekendMealId(UUID userId, UUID weekendMealId) {
        return weekendMealApplyRepository.findByUserIdAndWeekendMealId(userId, weekendMealId)
                .map(WeekendMealApplyEntity::getIsApplied)
                .orElse(false);
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
                .where(weekendMealApplyEntity.isApplied.eq(true))
                .fetch()
                .stream()
                .map(weekendMealApplyMapper::entityToDomain)
                .toList();
    }
}
