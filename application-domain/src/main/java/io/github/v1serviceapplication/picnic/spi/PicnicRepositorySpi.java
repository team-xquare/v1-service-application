package io.github.v1serviceapplication.picnic.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.picnic.Picnic;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Spi
public interface PicnicRepositorySpi {
    void applyWeekendPicnic(Picnic picnic);

    List<Picnic> findAll();

    List<Picnic> findAllByToday();

    List<UUID> findUserIdByToday();

    void updateDormitoryReturnTime(UUID picnicId);

    Optional<Picnic> findByPicnicId(UUID picnicId);

    void acceptPicnic(UUID picnicId);

    void refusePicnic(UUID picnicId);
}
