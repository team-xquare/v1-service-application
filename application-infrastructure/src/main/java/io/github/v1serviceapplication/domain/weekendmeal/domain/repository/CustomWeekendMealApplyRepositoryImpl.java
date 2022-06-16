package io.github.v1serviceapplication.domain.weekendmeal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.weekendmeal.domain.WeekendMealApplyEntity;
import io.github.v1serviceapplication.domain.weekendmeal.mapper.WeekendMealApplyMapper;
import io.github.v1serviceapplication.weekendmeal.WeekendMealApply;
import io.github.v1serviceapplication.weekendmeal.exception.WeekendMealApplyNotFoundException;
import io.github.v1serviceapplication.weekendmeal.postweekendmeal.spi.PostWeekendMealApplyRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.weekendmeal.domain.QWeekendMealApplyEntity.weekendMealApplyEntity;

@RequiredArgsConstructor
@Repository
public class CustomWeekendMealApplyRepositoryImpl implements PostWeekendMealApplyRepositorySpi {

    private final WeekendMealApplyRepository weekendMealApplyRepository;
    private final WeekendMealApplyMapper weekendMealApplyMapper;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean todayWeekendMealApplyExist(UUID userId) {
        return jpaQueryFactory
                .select(weekendMealApplyEntity.count())
                .from(weekendMealApplyEntity)
                .where(weekendMealApplyEntity.userId.eq(userId))
                .orderBy(weekendMealApplyEntity.date.asc())
                .limit(1)
                .fetchFirst()
                .equals(1L);
    }

    @Override
    public void saveWeekendMealApply(WeekendMealApply weekendMealApply) {
        weekendMealApplyRepository.save(
                weekendMealApplyMapper.domainToEntity(weekendMealApply)
        );
    }

    @Override
    @Transactional
    public void updateWeekendMealApply(UUID userId, Boolean apply) {
        WeekendMealApplyEntity weekendMealApply =  weekendMealApplyRepository.findTop1ByUserIdOrderByDateAsc(userId)
                .orElseThrow(() -> WeekendMealApplyNotFoundException.EXCEPTION);

        weekendMealApply.updateApply(apply);
    }
}
