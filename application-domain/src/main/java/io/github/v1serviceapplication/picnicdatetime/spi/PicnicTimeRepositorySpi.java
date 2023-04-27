package io.github.v1serviceapplication.picnicdatetime.spi;

import io.github.v1serviceapplication.picnicdatetime.TimeType;

import java.time.LocalTime;

public interface PicnicTimeRepositorySpi {

    LocalTime getPicnicTime(TimeType type);
}
