package io.github.v1serviceapplication.picnic.spi;

import io.github.v1serviceapplication.annotation.Spi;
import io.github.v1serviceapplication.picnic.Picnic;

@Spi
public interface PicnicRepositorySpi {
    void applyWeekendPicnic(Picnic picnic);
}
