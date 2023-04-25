package io.github.v1serviceapplication.domain.picnicdatetime.domain.repository;

import io.github.v1serviceapplication.domain.picnicdatetime.domain.PicnicDateTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PicnicDateTimeRepository extends JpaRepository<PicnicDateTimeEntity, UUID> {
}
