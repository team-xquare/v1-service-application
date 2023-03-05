package io.github.v1serviceapplication.infrastructure.feign.client.point;

import io.github.v1serviceapplication.infrastructure.feign.client.dto.response.PointStatusResponse;
import io.github.v1serviceapplication.stay.api.dto.response.UserPointStatusResponse;
import io.github.v1serviceapplication.stay.spi.PointUserFeignSpi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PointClientImpl implements PointUserFeignSpi {

    private final PointClient pointClient;

    @Override
    public UserPointStatusResponse getUserPointStatus(UUID userId) {
        PointStatusResponse pointStatus = pointClient.queryPointStatusByUserId(userId);
        return new UserPointStatusResponse(
                pointStatus.getGoodPoint(),
                pointStatus.getBadPoint()
        );
    }
}
