package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.stay.api.SetDefaultStay;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class SetDefaultStayImpl implements SetDefaultStay {
    private final StayRepositorySpi stayRepositorySpi;

    @Override
    public void setDefaultStay(UUID userId) {
        stayRepositorySpi.setDefaultStay(userId);
    }
}
