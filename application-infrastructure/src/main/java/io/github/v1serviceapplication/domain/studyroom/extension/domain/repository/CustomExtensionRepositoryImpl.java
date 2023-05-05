package io.github.v1serviceapplication.domain.studyroom.extension.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.domain.studyroom.mapper.ExtensionMapper;
import io.github.v1serviceapplication.studyroom.extension.Extension;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomPostExtensionRepositorySpi;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.studyroom.extension.domain.QExtensionEntity.extensionEntity;

@RequiredArgsConstructor
@Repository
public class CustomExtensionRepositoryImpl implements StudyRoomQueryExtensionRepositorySpi, StudyRoomPostExtensionRepositorySpi {

    private final JPAQueryFactory queryFactory;
    private final ExtensionRepository extensionRepository;
    private final ExtensionMapper extensionMapper;

    @Override
    public List<UUID> findStudentIdByRoomIdAndToday(UUID studyRoomId) {
        return queryFactory.select(extensionEntity.userId)
                .from(extensionEntity)
                .where(
                        extensionEntity.studyRoom.id.eq(studyRoomId)
                                .and(extensionEntity.createDate.eq(LocalDate.now()))
                )
                .fetch();
    }

    @Override
    public Extension todayStudyRoomApply(UUID userId) {
        return extensionMapper.extensionEntityToDomain(
                queryFactory
                        .select(extensionEntity)
                        .from(extensionEntity)
                        .where(
                                extensionEntity.userId.eq(userId)
                                        .and(
                                                extensionEntity.createDate.eq(LocalDate.now())
                                        )
                        )
                        .fetchFirst()
        );
    }

    @Override
    public void deleteById(UUID extensionId) {
        extensionRepository.deleteById(extensionId);
    }

}
