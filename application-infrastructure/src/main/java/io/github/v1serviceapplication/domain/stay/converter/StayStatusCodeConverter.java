package io.github.v1serviceapplication.domain.stay.converter;

import io.github.v1serviceapplication.stay.code.StayStatusCode;
import io.github.v1serviceapplication.global.error.exception.ConvertFailedException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Converter
public class StayStatusCodeConverter implements AttributeConverter<StayStatusCode, String> {

    private static final Map<String, StayStatusCode> MAP =
            Collections.unmodifiableMap(Arrays.stream(StayStatusCode.values())
                    .collect(Collectors.toMap(StayStatusCode::getCode, Function.identity())));

    public static StayStatusCode find(String dbData) {
        return Optional.of(MAP.get(dbData))
                .orElseThrow(() -> ConvertFailedException.EXCEPTION);
    }

    @Override
    public String convertToDatabaseColumn(StayStatusCode attribute) {
        return attribute.getCode();
    }

    @Override
    public StayStatusCode convertToEntityAttribute(String dbData) {
        return find(dbData);
    }
}
