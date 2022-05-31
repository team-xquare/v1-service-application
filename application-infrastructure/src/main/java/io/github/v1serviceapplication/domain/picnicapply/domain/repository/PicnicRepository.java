package io.github.v1serviceapplication.domain.picnicapply.domain.repository;

import io.github.v1serviceapplication.domain.picnicapply.domain.PicnicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PicnicRepository extends JpaRepository<PicnicEntity, UUID> {
}
