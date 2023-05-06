package io.github.v1serviceapplication.domain.studyroom.extension.domain.repository;

import io.github.v1serviceapplication.domain.studyroom.extension.domain.ExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, UUID> {
    Optional<ExtensionEntity> findByUserIdAndCreateDate(UUID userId, LocalDate createDate);
}
