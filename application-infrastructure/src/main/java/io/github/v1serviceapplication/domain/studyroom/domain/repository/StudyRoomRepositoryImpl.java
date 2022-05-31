package io.github.v1serviceapplication.domain.studyroom.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.QStudyRoomVO;
import io.github.v1serviceapplication.domain.studyroom.domain.repository.vo.StudyRoomVO;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.StudyRoomRepositorySpi;
import io.github.v1serviceapplication.studyroom.querystudyroom.spi.dto.StudyRoomModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.github.v1serviceapplication.domain.studyroom.domain.QStudyRoomEntity.studyRoomEntity;
import static io.github.v1serviceapplication.domain.extension.domain.QExtensionEntity.extensionEntity;

@RequiredArgsConstructor
@Repository
public class StudyRoomRepositoryImpl implements StudyRoomRepositorySpi {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StudyRoomModel> findAll() {
        List<StudyRoomVO> studyRoomVOList = queryFactory
                        .select(
                                new QStudyRoomVO(
                                        studyRoomEntity.id,
                                        studyRoomEntity.name,
                                        extensionEntity.count().intValue()
                                )
                        )
                        .from(studyRoomEntity)
                        .leftJoin(extensionEntity.studyRoom, studyRoomEntity)
                        .fetch();

        return studyRoomVOList.stream()
                .map(
                        studyRoomVO ->
                            new StudyRoomModel(
                                    studyRoomVO.getId(),
                                    studyRoomVO.getName(),
                                    studyRoomVO.getApplicationCount(),
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

}
