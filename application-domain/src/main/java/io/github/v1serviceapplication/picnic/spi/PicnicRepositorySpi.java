package io.github.v1serviceapplication.picnic.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.picnic.Picnic;
import io.github.v1serviceapplication.picnic.api.dto.PicnicUserElement;
import io.github.v1serviceapplication.picnic.api.dto.UpdatePicnicDomainRequest;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Spi
public interface PicnicRepositorySpi {
    void applyWeekendPicnic(Picnic picnic);

    List<Picnic> findAll();

    List<Picnic> findAllByToday(List<LocalTime> picnicRequestTime);

    List<UUID> findUserIdByToday(List<LocalTime> picnicRequestTime);

    Optional<Picnic> findByPicnicId(UUID picnicId);

    List<Picnic> findAllByUserIdAndDormitoryReturnCheckTime(UUID userId);

    PicnicUserElement getUserInfo(UUID userId);

    void updateWeekendPicnic(UUID picnicId, UpdatePicnicDomainRequest request);

    Picnic findByUserIdAndCreateDateTimeByPresentPicnic(UUID userId, List<LocalTime> picnicRequestTime);

    void deletePicnic(UUID userId);
}
