package io.github.v1serviceapplication.domain.stayapply.domain.repository;

import io.github.v1serviceapplication.domain.stayapply.domain.StayApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StayApplyRepository extends JpaRepository<StayApplyEntity, UUID> {
}
