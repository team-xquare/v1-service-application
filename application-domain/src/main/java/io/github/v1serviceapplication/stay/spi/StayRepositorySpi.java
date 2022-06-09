package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.stay.code.StayStatusCode;

@Spi
public interface StayRepositorySpi {
    void applyStay(StayStatusCode status);
}
