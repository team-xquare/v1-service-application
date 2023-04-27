package io.github.v1serviceapplication.picnicdatetime.spi;

import io.github.v1serviceapplication.picnicdatetime.DateTimeType;

import java.time.LocalTime;

public interface PicnicDateTimeRepositorySpi {

    LocalTime getPicnicTime(DateTimeType type);
}
