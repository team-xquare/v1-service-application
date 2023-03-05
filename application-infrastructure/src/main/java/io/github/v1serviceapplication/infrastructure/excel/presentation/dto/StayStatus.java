package io.github.v1serviceapplication.infrastructure.excel.presentation.dto;

import io.github.v1serviceapplication.global.error.exception.InvalidStayStatusException;
import io.github.v1serviceapplication.infrastructure.excel.StayOption;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class StayStatus {
    private final UUID userId;

    private final String name;

    private final String num;

    private String stay;

    public void setStay(String stay) {
        if(List.of(StayOption.VALUES).contains(stay)) {
            this.stay = stay;
        }
        else {
            throw InvalidStayStatusException.EXCEPTION;
        }
    }

    public void setStay(Integer stay) {
        if(stay < 1 || stay > 4) {
            throw InvalidStayStatusException.EXCEPTION;
        }
    }

    public static String getStayValue(Integer index) {
        if(index < 1 || index > 4) {
            throw InvalidStayStatusException.EXCEPTION;
        }
        return StayOption.VALUES[index - 1];
    }
}
