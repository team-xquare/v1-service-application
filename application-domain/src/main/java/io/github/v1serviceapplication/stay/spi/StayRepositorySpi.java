package io.github.v1serviceapplication.stay.spi;

import io.github.v1serviceapplication.annotation.Spi;

@Spi
public interface StayRepositorySpi {
    void applyStay(String status);
}
