package io.github.v1serviceapplication.domain.picnic.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.domain.picnic.mapper.PicnicMapper;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.picnic.domain.QPicnicEntity.picnicEntity;

@Repository
@RequiredArgsConstructor
public class PicnicRepositorySpiImpl implements PicnicRepositorySpi {
    private final PicnicRepository picnicRepository;
    private final PicnicMapper picnicMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public void applyWeekendPicnic(Picnic picnic) {
        picnicRepository.save(picnicMapper.picnicDomainToEntity(picnic));
    }

    @Override
    public List<Picnic> findAll() {
        List<PicnicEntity> picnicEntity = picnicRepository.findAll();

        return picnicEntity.stream().map(picnicMapper::picnicEntityToDomain).toList();
    }

    @Override
    public List<Picnic> findAllByToday() {
        return queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.date.eq(LocalDate.now()))
                .fetch()
                .stream().map(picnicMapper::picnicEntityToDomain)
                .toList();
    }

    @Override
    public List<UUID> findUserIdByToday() {
        List<PicnicEntity> test = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.date.eq(LocalDate.now()))
                .fetch();

        return test.stream().map(PicnicEntity::getUserId).toList();
    }

    @Transactional
    @Override
    public void updateDormitoryReturnTime(UUID picnicId) {
        queryFactory
                .update(picnicEntity)
                .set(picnicEntity.dormitoryReturnCheckTime, LocalTime.now())
                .where(picnicEntity.id.eq(picnicId))
                .execute();
    }

    @Override
    public Optional<Picnic> findByPicnicId(UUID picnicId) {
        PicnicEntity entity = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.id.eq(picnicId))
                .fetchOne();

        return Optional.ofNullable(picnicMapper.picnicEntityToDomain(entity));
    }
}
