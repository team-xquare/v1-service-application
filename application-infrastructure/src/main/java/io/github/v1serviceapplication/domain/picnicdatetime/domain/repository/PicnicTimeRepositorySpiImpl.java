package io.github.v1serviceapplication.domain.picnicdatetime.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.picnicdatetime.TimeType;
import io.github.v1serviceapplication.picnicdatetime.spi.PicnicTimeRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

import static io.github.v1serviceapplication.domain.picnicdatetime.domain.QPicnicTimeEntity.picnicTimeEntity;

@Repository
@RequiredArgsConstructor
public class PicnicTimeRepositorySpiImpl implements PicnicTimeRepositorySpi {
    private final JPAQueryFactory queryFactory;

    @Override
    public LocalTime getPicnicTime(TimeType type) {
        LocalTime picnicTime = queryFactory
                .select(picnicTimeEntity.picnicTime)
                .from(picnicTimeEntity)
                .where(picnicTimeEntity.timeType.eq(type))
                .fetchOne();

        return picnicTime;
    }
}
