package io.github.v1serviceapplication.domain.studyroom.domain.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.extension.domain.repository.ExtensionRepository;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.QStudyRoomVO;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.StudyRoomVO;
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
public class CustomStudyRoomRepositoryImpl implements StudyRoomRepositorySpi {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StudyRoomModel> findAll() {
        List<StudyRoomVO> studyRoomVOList = queryFactory
                .select(
                        new QStudyRoomVO(
                                studyRoomEntity.id,
                                studyRoomEntity.name,
                                extensionCount()
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
                                        .studentList(queryStudentId(studyRoomVO.getId()))
                                        .build()
                ).collect(Collectors.toList());
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
                .where(extensionEntity.studyRoom.id.eq(studyRoomEntity.id));
    }

}