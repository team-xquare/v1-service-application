package io.github.v1serviceapplication.domain.extensionapply.domain.repository;

import io.github.v1serviceapplication.domain.extensionapply.domain.ExtensionApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExtensionApplyRepository extends JpaRepository<ExtensionApplyEntity, UUID> {
}
