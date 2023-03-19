package io.github.v1serviceapplication.picnic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WeekendPicnicExcelListResponse {
    private List<WeekendPicnicExcelElement> weekendPicnicList;
}
