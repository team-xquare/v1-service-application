package io.github.v1serviceapplication.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CodeElement {
    private final String name;

    private final String value;
}
