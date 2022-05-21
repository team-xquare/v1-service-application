package io.github.v1serviceapplication.domain.studyroom.domain.repository;

import io.github.v1serviceapplication.domain.studyroom.domain.StudyRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoomEntity, UUID> {
}
