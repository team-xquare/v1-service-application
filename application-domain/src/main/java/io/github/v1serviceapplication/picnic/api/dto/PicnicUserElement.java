package io.github.v1serviceapplication.picnic.api.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PicnicUserElement {
    private final UUID userId;
    private final String num;
    private final String name;
}
