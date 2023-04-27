package io.github.v1serviceapplication.domain.picnicdatetime.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.picnicdatetime.DateTimeType;
import io.github.v1serviceapplication.picnicdatetime.spi.PicnicDateTimeRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

import static io.github.v1serviceapplication.domain.picnicdatetime.domain.QPicnicDateTimeEntity.picnicDateTimeEntity;

@Repository
@RequiredArgsConstructor
public class PicnicDateTimeRepositorySpiImpl implements PicnicDateTimeRepositorySpi {
    private final JPAQueryFactory queryFactory;

    @Override
    public LocalTime getPicnicTime(DateTimeType type) {
        LocalTime picnicTime = queryFactory
                .select(picnicDateTimeEntity.picnicTime)
                .from(picnicDateTimeEntity)
                .where(picnicDateTimeEntity.dateTimeType.eq(type))
                .fetchOne();

        return picnicTime;
    }
}
