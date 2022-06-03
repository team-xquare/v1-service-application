package io.github.v1serviceapplication.domain.stay.code;

import io.github.v1serviceapplication.global.error.exception.AttributeConvertFailedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum StayStatusCode {
    STAY("AP0001", "잔류"),
    FRIDAY_RETURN_HOME("AP0002", "금요 귀가"),
    SATURDAY_RETURN_HOME("AP0003", "토요 귀가"),
    SATURDAY_RETURN_DORM("AP0004", "토요 귀사");

    private final String code;
    private final String value;

    private static final Map<String, StayStatusCode> MAP =
            Collections.unmodifiableMap(Arrays.stream(StayStatusCode.values())
                    .collect(Collectors.toMap(StayStatusCode::getCode, Function.identity())));

    public static StayStatusCode find(String dbData) {
        return Optional.of(MAP.get(dbData))
                .orElseThrow(() -> AttributeConvertFailedException.EXCEPTION);
    }

    @Converter
    public static class StayStatusCodeConverter implements AttributeConverter<StayStatusCode, String> {

        @Override
        public String convertToDatabaseColumn(StayStatusCode attribute) {
            return attribute.getCode();
        }

        @Override
        public StayStatusCode convertToEntityAttribute(String dbData) {
            return find(dbData);
        }
    }
}
