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
import static java.lang.String.format;

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
    public List<Picnic> findAllByToday(List<LocalTime> picnicRequestTime) {
        return queryFactory
                .selectFrom(picnicEntity)
                .where(checkValidLocalDateTime(picnicEntity.createDateTime, picnicRequestTime))
                .fetch()
                .stream().map(picnicMapper::picnicEntityToDomain)
                .toList();
    }

    @Override
    public List<UUID> findUserIdByToday(List<LocalTime> picnicRequestTime) {
        List<PicnicEntity> entityList = queryFactory
                .selectFrom(picnicEntity)
                .where(checkValidLocalDateTime(picnicEntity.createDateTime, picnicRequestTime))
                .fetch();

        return entityList.stream().map(PicnicEntity::getUserId).toList();
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
    public List<Picnic> findAllByUserIdAndDormitoryReturnCheckTime(UUID userId, List<LocalTime> picnicRequestTime) {
        List<PicnicEntity> entityList = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.userId.eq(userId)
                        .and(checkValidLocalDateTime(picnicEntity.createDateTime, picnicRequestTime))
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
    public Picnic findByUserIdAndCreateDateTimeByPresentPicnic(UUID userId, List<LocalTime> picnicRequestTime) {
        PicnicEntity entity = queryFactory
                .selectFrom(picnicEntity)
                .where(picnicEntity.userId.eq(userId)
                        .and(checkValidLocalDateTime(picnicEntity.createDateTime, picnicRequestTime))
                )
                .fetchOne();

        if (entity == null) {
            throw PicnicNotFoundException.EXCEPTION;
        }

        return picnicMapper.picnicEntityToDomain(entity);
    }

    @Transactional
    @Override
    public void updateWeekendPicnic(UUID picnicId, UpdatePicnicDomainRequest request) {
        PicnicEntity picnic = picnicRepository.findById(picnicId)
                .orElseThrow(() -> PicnicNotFoundException.EXCEPTION);

        picnic.updatePicnic(request.getStartTime(), request.getEndTime(), request.getReason(), request.getArrangement());
    }

    @Transactional
    @Override
    public void deletePicnic(UUID userId) {
        queryFactory
                .delete(picnicEntity)
                .where(picnicEntity.userId.eq(userId))
                .execute();
    }

    private BooleanExpression checkValidLocalDateTime(DateTimePath<LocalDateTime> createDateTime, List<LocalTime> picnicTime) {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();

        if (LocalTime.now().isAfter(picnicTime.get(0))) {
            startDate = LocalDate.now();
            endDate = LocalDate.now().plusDays(1);
        }

        return createDateTime.between(
                LocalDateTime.of(startDate, picnicTime.get(0)),
                LocalDateTime.of(endDate, picnicTime.get(1))
        );
    }
}
