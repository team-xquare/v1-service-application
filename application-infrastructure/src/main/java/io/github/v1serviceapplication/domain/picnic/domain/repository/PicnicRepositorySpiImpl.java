package io.github.v1serviceapplication.domain.picnic.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.picnic.domain.PicnicEntity;
import io.github.v1serviceapplication.domain.picnic.mapper.PicnicMapper;
import io.github.v1serviceapplication.error.PicnicNotFoundException;
import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.UserInfoResponseElement;
import io.github.v1serviceapplication.infrastructure.feign.client.user.UserClient;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.api.dto.UpdatePicnicDomainRequest;
import io.github.v1serviceapplication.picnic.spi.PicnicRepositorySpi;
import io.github.v1serviceapplication.picnicdatetime.TimeType;
import io.github.v1serviceapplication.picnicdatetime.spi.PicnicTimeRepositorySpi;
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
    private final PicnicTimeRepositorySpi picnicDateTimeRepositorySpi;

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
        List<LocalTime> picnicRequestAllowTime = picnicDateTimeRepositorySpi.getPicnicAllowTime(List.of(TimeType.PICNIC_REQUEST_START_TIME, TimeType.PICNIC_REQUEST_END_TIME));

        return queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.createDateTime.between(
                                LocalDateTime.of(LocalDate.now().minusDays(1), picnicRequestAllowTime.get(0)),
                                LocalDateTime.of(LocalDate.now(), picnicRequestAllowTime.get(1)))
                )
                .fetch()
                .stream().map(picnicMapper::picnicEntityToDomain)
                .toList();
    }

    @Override
    public List<UUID> findUserIdByToday() {
        List<LocalTime> picnicRequestAllowTime = picnicDateTimeRepositorySpi.getPicnicAllowTime(List.of(TimeType.PICNIC_REQUEST_START_TIME, TimeType.PICNIC_REQUEST_END_TIME));

        List<PicnicEntity> entityList = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.createDateTime.between(
                        LocalDateTime.of(LocalDate.now().minusDays(1), picnicRequestAllowTime.get(0)),
                        LocalDateTime.of(LocalDate.now(), picnicRequestAllowTime.get(1)))
                )
                .fetch();

        return entityList.stream().map(PicnicEntity::getUserId).toList();
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

    @Override
    public Optional<Picnic> findByUserIdAndCreateDateTimeByPresentPicnic(UUID userId) {
        PicnicEntity entity = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.userId.eq(userId)
                        .and(picnicEntity.dormitoryReturnCheckTime.isNull())
                        .and(checkValidTime(picnicEntity.createDateTime))
                        .and(picnicEntity.isAcceptance.eq(true))
                )
                .fetchOne();

        if (entity.equals(null)) {
            System.out.println("adk;lfjasl;kdfjk;saldflke");
        }

        return Optional.ofNullable(picnicMapper.picnicEntityToDomain(entity));
    }

    @Transactional
    @Override
    public void updateWeekendPicnic(UUID picnicId, UpdatePicnicDomainRequest request) {
        PicnicEntity picnic = picnicRepository.findById(picnicId)
                .orElseThrow(()-> PicnicNotFoundException.EXCEPTION);

        picnic.updatePicnic(request.getStartTime(), request.getEndTime(), request.getReason(), request.getArrangement());
    }

    @Override
    public Optional<Picnic> findByUserIdAndCreateDateTimeByPresentPicnic(UUID userId) {
        List<LocalTime> picnicRequestAllowTime = picnicDateTimeRepositorySpi.getPicnicAllowTime(List.of(TimeType.PICNIC_REQUEST_START_TIME, TimeType.PICNIC_REQUEST_END_TIME));

        PicnicEntity entity = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.userId.eq(userId)
                        .and(picnicEntity.dormitoryReturnCheckTime.isNull())
                        .and(picnicEntity.createDateTime.between(
                                LocalDateTime.of(LocalDate.now().minusDays(1), picnicRequestAllowTime.get(0)),
                                LocalDateTime.of(LocalDate.now(), picnicRequestAllowTime.get(1)))
                        )
                        .and(picnicEntity.isAcceptance.eq(true))
                )
                .fetchOne();
        return Optional.ofNullable(picnicMapper.picnicEntityToDomain(entity));
    }


    @Transactional
    @Override
    public void deletePicnic(UUID userId) {
        queryFactory
                .delete(picnicEntity)
                .where(picnicEntity.userId.eq(userId))
                .execute();
    }

    private BooleanExpression checkValidTime(DateTimePath<LocalDateTime> createDateTime) {
        List<LocalTime> picnicRequestAllowTime = picnicDateTimeRepositorySpi.getPicnicAllowTime(List.of(TimeType.PICNIC_REQUEST_START_TIME, TimeType.PICNIC_REQUEST_END_TIME));

        return createDateTime.between(
                LocalDateTime.of(LocalDate.now().minusDays(1), picnicRequestAllowTime.get(0)),
                LocalDateTime.of(LocalDate.now(), picnicRequestAllowTime.get(1))
        );
    }
}
