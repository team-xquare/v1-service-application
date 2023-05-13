package io.github.v1serviceapplication.picnic.api;

import io.github.v1serviceapplication.picnic.api.dto.ApplyWeekendPicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.PicnicAllowTimeResponse;
import io.github.v1serviceapplication.picnic.api.dto.PicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.PicnicListResponse;
import io.github.v1serviceapplication.picnic.api.dto.StudentPicnicDetail;
import io.github.v1serviceapplication.picnic.api.dto.UpdatePicnicDomainRequest;
import io.github.v1serviceapplication.picnic.api.dto.WeekendPicnicExcelListResponse;

import java.util.UUID;

public interface PicnicApi {
    void applyWeekendPicnic(ApplyWeekendPicnicDomainRequest request);

    PicnicListResponse weekendPicnicList();

    void updateDormitoryReturnTime(UUID picnicId);

    PicnicDetail getPicnicDetail(UUID picnicId);

    WeekendPicnicExcelListResponse weekendPicnicExcel();

    StudentPicnicDetail getStudentPicnicDetail();

    void updateWeekendPicnic(UpdatePicnicDomainRequest request);

    void deleteWeekendPicnic();

    PicnicAllowTimeResponse getPicnicAllowTime();
}
