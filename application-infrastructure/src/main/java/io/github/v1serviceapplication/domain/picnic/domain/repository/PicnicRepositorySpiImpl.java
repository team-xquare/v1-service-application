package io.github.v1serviceapplication.domain.picnic.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.domain.picnic.mapper.PicnicMapper;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponseElement;
import io.github.v1serviceapplication.infrastructure.feign.client.user.UserClient;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.picnic.domain.QPicnicEntity.picnicEntity;
import static java.lang.String.*;

@Repository
@RequiredArgsConstructor
public class PicnicRepositorySpiImpl implements PicnicRepositorySpi {
    private final PicnicRepository picnicRepository;
    private final PicnicMapper picnicMapper;
    private final JPAQueryFactory queryFactory;
    private final UserClient userClient;

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
        LocalDateTime dateStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withNano(0);
        LocalDateTime dateEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).withNano(0);

        LocalDateTime picnicRequestStartTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(20, 30));
        LocalDateTime picnicRequestEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));

        return queryFactory
                .selectFrom(picnicEntity)
                .where(
                        picnicEntity.createDateTime.between(dateStartTime, dateEndTime),
                        picnicEntity.createDateTime.between(picnicRequestStartTime, picnicRequestEndTime)
                )
                .fetch()
                .stream().map(picnicMapper::picnicEntityToDomain)
                .toList();
    }

    @Override
    public List<UUID> findUserIdByToday() {
        LocalDateTime dateStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withNano(0);
        LocalDateTime dateEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).withNano(0);

        LocalDateTime picnicRequestStartTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(20, 30));
        LocalDateTime picnicRequestEndTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30));

        return queryFactory
                .select(picnicEntity.userId)
                .from(picnicEntity)
                .where(
                        picnicEntity.createDateTime.between(dateStartTime, dateEndTime),
                        picnicEntity.createDateTime.between(picnicRequestStartTime, picnicRequestEndTime)
                )
                .fetch();
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

    @Transactional
    @Override
    public void acceptPicnic(UUID picnicId) {
        queryFactory
                .update(picnicEntity)
                .set(picnicEntity.isAcceptance, true)
                .where(picnicEntity.id.eq(picnicId))
                .execute();
    }

    @Transactional
    @Override
    public void refusePicnic(UUID picnicId) {
        queryFactory
                .delete(picnicEntity)
                .where(picnicEntity.id.eq(picnicId))
                .execute();
    }

    @Transactional
    @Override
    public List<Picnic> findAllByUserIdAndIsAcceptance(UUID userId) {
        List<PicnicEntity> entityList = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.userId.eq(userId)
                        .and(picnicEntity.isAcceptance.eq(true))
                        .and(picnicEntity.dormitoryReturnCheckTime.isNull())
                )
                .fetch();
        return entityList.stream().map(picnicMapper::picnicEntityToDomain).toList();
    }

    @Override
    public PicnicUserElement getUserInfo(UUID userId) {
        UserInfoResponseElement userInfo = userClient.queryUserInfo(userId);
        return new PicnicUserElement(
                userInfo.getId(),
                userInfo.getGrade().toString() + userInfo.getClassNum().toString() + format("%02d", userInfo.getNum()),
                userInfo.getName()
        );
    }
}
