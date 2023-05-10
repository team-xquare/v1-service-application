package io.github.v1serviceapplication.picnicdatetime.spi;

import io.github.v1serviceapplication.picnicdatetime.TimeType;

import java.time.LocalTime;
import java.util.List;

public interface PicnicTimeRepositorySpi {

    List<LocalTime> getPicnicAllowTime(List<TimeType> types);
}
