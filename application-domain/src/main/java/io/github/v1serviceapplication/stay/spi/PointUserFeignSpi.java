package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.stay.api.dto.response.UserPointStatusResponse;

import java.util.UUID;

@Spi
public interface PointUserFeignSpi {
    UserPointStatusResponse getUserPointStatus(UUID userId);
}
