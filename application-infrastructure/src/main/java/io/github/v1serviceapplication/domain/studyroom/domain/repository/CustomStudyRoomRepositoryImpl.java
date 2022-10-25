package io.github.v1serviceapplication.domain.studyroom.domain.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.QStudyRoomVO;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.StudyRoomVO;
import io.github.v1serviceapplication.domain.studyroom.exception.ExtensionNotFoundException;
import io.github.v1serviceapplication.domain.studyroom.exception.StudyRoomNotFoundException;
import io.github.v1serviceapplication.domain.studyroom.extension.domain.ExtensionEntity;
import io.github.v1serviceapplication.domain.studyroom.extension.domain.repository.ExtensionRepository;
import io.github.v1serviceapplication.domain.studyroom.mapper.StudyRoomMapper;
import io.github.v1serviceapplication.studyroom.StudyRoom;
import io.github.v1serviceapplication.studyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.QueryStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.count;
import static io.github.v1serviceapplication.domain.studyroom.domain.QStudyRoomEntity.studyRoomEntity;
import static io.github.v1serviceapplication.domain.studyroom.extension.domain.QExtensionEntity.extensionEntity;

@RequiredArgsConstructor
@Repository
public class CustomStudyRoomRepositoryImpl implements QueryStudyRoomRepositorySpi, PostStudyRoomRepositorySpi {

    private final StudyRoomRepository studyRoomRepository;
    private final ExtensionRepository extensionRepository;
    private final JPAQueryFactory queryFactory;

    private final StudyRoomMapper studyRoomMapper;

    @Override
    public List<StudyRoomModel> findAll() {
        List<StudyRoomVO> studyRoomVOList = queryFactory
                .select(
                        new QStudyRoomVO(
                                studyRoomEntity.id,
                                studyRoomEntity.name,
                                extensionCount(),
                                studyRoomEntity.maxPeopleCount
                        )
                )
                .from(studyRoomEntity)
                .fetch();

        return studyRoomVOList.stream()
                .map(
                        studyRoomVO ->
                                StudyRoomModel.builder()
                                        .id(studyRoomVO.getId())
                                        .name(studyRoomVO.getName())
                                        .applicationCount(studyRoomVO.getApplicationCount().intValue())
                                        .maxPeopleCount(studyRoomVO.getMaxPeopleCount())
                                        .studentList(queryStudentId(studyRoomVO.getId()))
                                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public Optional<StudyRoom> findStudyRoomIdByUserId(UUID userId) {
        return extensionRepository.findByUserIdAndDate(userId, LocalDate.now())
                .map(ExtensionEntity::getStudyRoom)
                .map(studyRoomMapper::studyRoomEntityToDomain);
    }

    private List<UUID> queryStudentId(UUID studyRoomId) {
        return queryFactory
                .select(extensionEntity.userId)
                .from(extensionEntity)
                .where(extensionEntity.studyRoom.id.eq(studyRoomId))
                .fetch();
    }

    private JPQLQuery<Long> extensionCount() {
        return JPAExpressions.select(count(extensionEntity.userId))
                .from(extensionEntity)
                .where(
                        extensionEntity.studyRoom.id.eq(studyRoomEntity.id)
                                .and(getCurrentDatePredicate())
                );
    }


    @Override
    public boolean todayStudyRoomApplyExist(UUID userId) {
        return queryFactory
                .select(extensionEntity)
                .from(extensionEntity)
                .where(
                        extensionEntity.userId.eq(userId)
                                .and(
                                        extensionEntity.date.eq(LocalDate.now())
                                )
                )
                .fetchFirst() != null;
    }

    @Override
    public Long applicationCount(UUID studyRoomId) {
        return queryFactory
                .select(extensionEntity.count())
                .from(extensionEntity)
                .where(
                        extensionEntity.studyRoom.id.eq(studyRoomId)
                                .and(getCurrentDatePredicate())
                )
                .fetchFirst();
    }

    @Override
    public StudyRoom findById(UUID studyRoomId) {
        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> StudyRoomNotFoundException.EXCEPTION);

        return studyRoomMapper.studyRoomEntityToDomain(studyRoom);
    }

    @Override
    public void postStudyRoom(UUID studyRoomId, UUID userId) {
        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> StudyRoomNotFoundException.EXCEPTION);

        extensionRepository.save(
                ExtensionEntity.builder()
                        .userId(userId)
                        .studyRoom(studyRoom)
                        .build()
        );

    }

    @Override
    @Transactional
    public void updateStudyRoom(UUID studyRoomId, UUID userId) {
        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(() -> StudyRoomNotFoundException.EXCEPTION);

        extensionRepository.findByUserIdAndDate(userId, LocalDate.now())
                .orElseThrow(() -> ExtensionNotFoundException.EXCEPTION)
                .changeStudyRoom(studyRoom);
    }

    private Predicate getCurrentDatePredicate() {
        return extensionEntity.date.eq(LocalDate.now());
    }

}