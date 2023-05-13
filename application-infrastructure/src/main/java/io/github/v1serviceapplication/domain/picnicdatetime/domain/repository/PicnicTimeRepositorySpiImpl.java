package io.github.v1serviceapplication.domain.picnicdatetime.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.picnicdatetime.domain.mapper.PicnicTimeMapper;
import io.github.v1serviceapplication.picnicdatetime.PicnicTime;
import io.github.v1serviceapplication.picnicdatetime.TimeType;
import io.github.v1serviceapplication.picnicdatetime.spi.PicnicTimeRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

import static io.github.v1serviceapplication.domain.picnicdatetime.domain.QPicnicTimeEntity.picnicTimeEntity;

@Repository
@RequiredArgsConstructor
public class PicnicTimeRepositorySpiImpl implements PicnicTimeRepositorySpi {
    private final JPAQueryFactory queryFactory;
    private final PicnicTimeMapper picnicTimeMapper;

    @Override
    public List<PicnicTime> getPicnicTime(List<TimeType> types) {
        List<PicnicTime> entity = queryFactory
                .selectFrom(picnicTimeEntity)
                .where(picnicTimeEntity.timeType.in(types))
                .fetch()
                .stream().map(picnicTimeMapper::picnicTimeEntityToDomain).toList();
        
        return entity;
    }

    @Override
    public List<LocalTime> getPicnicAllowTime(List<TimeType> types) {
        return queryFactory
                .select(picnicTimeEntity.picnicTime)
                .from(picnicTimeEntity)
                .where(picnicTimeEntity.timeType.in(types))
                .fetch();
    }
}
