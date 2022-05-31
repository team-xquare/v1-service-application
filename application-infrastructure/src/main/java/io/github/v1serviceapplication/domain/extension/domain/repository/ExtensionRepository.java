package io.github.v1serviceapplication.domain.extension.domain.repository;

import io.github.v1serviceapplication.domain.extension.domain.ExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExtensionRepository extends JpaRepository<ExtensionEntity, UUID> {
}
