package io.github.v1serviceapplication.domain.studyroom.domain.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.extension.domain.ExtensionEntity;
import io.github.v1serviceapplication.domain.extension.domain.repository.ExtensionRepository;
import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.QStudyRoomVO;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.StudyRoomVO;
import io.github.v1serviceapplication.studyroom.poststudyroom.spi.PostStudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.count;
import static io.github.v1serviceapplication.domain.extension.domain.QExtensionEntity.extensionEntity;
import static io.github.v1serviceapplication.domain.studyroom.domain.QStudyRoomEntity.studyRoomEntity;

@RequiredArgsConstructor
@Repository
public class CustomStudyRoomRepositoryImpl implements StudyRoomRepositorySpi, PostStudyRoomRepositorySpi {

    private final StudyRoomRepository studyRoomRepository;
    private final ExtensionRepository extensionRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StudyRoomModel> findAll() {
        List<StudyRoomVO> studyRoomVOList = queryFactory
                .select(
                        new QStudyRoomVO(
                                studyRoomEntity.id,
                                studyRoomEntity.name,
                                ExpressionUtils.as(
                                        JPAExpressions.select(count(extensionEntity.userId))
                                                .from(extensionEntity)
                                                .where(extensionEntity.studyRoom.id.eq(studyRoomEntity.id)),
                                        "count"
                                )

                        )
                )
                .from(studyRoomEntity)
                .fetch();

        return studyRoomVOList.stream()
                .map(
                        studyRoomVO ->
                                new StudyRoomModel(
                                        studyRoomVO.getId(),
                                        studyRoomVO.getName(),
                                        studyRoomVO.getApplicationCount().intValue(),
                                        queryStudentId(studyRoomVO.getId()))
                ).collect(Collectors.toList());
    }

    private List<UUID> queryStudentId(UUID studyRoomId) {
        return queryFactory
                .select(extensionEntity.userId)
                .from(extensionEntity)
                .where(extensionEntity.studyRoom.id.eq(studyRoomId))
                .fetch();
    }

    @Override
    public void postStudyRoom(UUID studyRoomId, UUID userId) {

        Long totalCount = queryFactory
                .select(extensionEntity.count())
                .from(extensionEntity)
                .where(extensionEntity.userId.eq(userId))
                .fetchFirst();

        if (totalCount >= 1) {
            throw new RuntimeException();
        }

        Long applicationCount = queryFactory
                .select(extensionEntity.count())
                .from(extensionEntity)
                .where(extensionEntity.studyRoom.id.eq(studyRoomId))
                .fetchFirst();

        StudyRoomEntity studyRoom = studyRoomRepository.findById(studyRoomId)
                .orElseThrow(RuntimeException::new);

        if (applicationCount >= studyRoom.getMaxPeopleCount()) {
            throw new RuntimeException();
        }

        extensionRepository.save(
                ExtensionEntity.builder()
                        .userId(userId)
                        .studyRoom(studyRoom)
                        .build()
        );

    }
}
