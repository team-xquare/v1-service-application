package io.github.v1serviceapplication.domain.picnicdatetime.domain.repository;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PicnicTimeRepository extends JpaRepository<PicnicTimeEntity, UUID> {
}
