package io.github.v1serviceapplication.picnic.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PicnicListResponse {
    private final List<PicnicElement> picnicList;
}
