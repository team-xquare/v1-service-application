package io.github.v1serviceapplication.domain.picnicapply.domain.repository;

import io.github.v1serviceapplication.domain.picnicapply.domain.PicnicApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PicnicApplyRepository extends JpaRepository<PicnicApplyEntity, UUID> {
}
