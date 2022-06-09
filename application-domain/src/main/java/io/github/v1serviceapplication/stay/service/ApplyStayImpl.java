package io.github.v1serviceapplication.stay.service;

import io.github.v1serviceapplication.annotation.DomainService;
import io.github.v1serviceapplication.stay.api.ApplyStay;
import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.stay.spi.StayRepositorySpi;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class ApplyStayImpl implements ApplyStay {
    private final StayRepositorySpi stayRepositorySpi;

    @Override
    public void applyStay(StayStatusCode status) {
        stayRepositorySpi.applyStay(status);
    }
}
