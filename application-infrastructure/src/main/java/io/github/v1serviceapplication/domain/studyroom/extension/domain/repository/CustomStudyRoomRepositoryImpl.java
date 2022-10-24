package io.github.v1serviceapplication.domain.studyroom.extension.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.v1serviceapplication.studyroom.spi.StudyRoomQueryExtensionRepositorySpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.github.v1serviceapplication.domain.studyroom.extension.domain.QExtensionEntity.extensionEntity;

@RequiredArgsConstructor
@Repository
public class CustomStudyRoomRepositoryImpl implements StudyRoomQueryExtensionRepositorySpi {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UUID> findTodayByStudyRoomId(UUID studyRoomId) {
        return queryFactory.select(extensionEntity.userId)
                .from(extensionEntity)
                .where(
                        extensionEntity.studyRoom.id.eq(studyRoomId)
                                .and(extensionEntity.date.eq(LocalDate.now()))
                )
                .fetch();
    }
}
