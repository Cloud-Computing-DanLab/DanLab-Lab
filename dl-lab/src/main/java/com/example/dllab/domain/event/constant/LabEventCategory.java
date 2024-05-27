package com.example.dllab.domain.event.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum LabEventCategory {
    PROJECT("프로젝트 정보"),
    EVENT("행사 정보")

    ;
    private final String category;
}
