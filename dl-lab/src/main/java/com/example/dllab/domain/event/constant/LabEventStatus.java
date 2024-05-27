package com.example.dllab.domain.event.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum LabEventStatus {
    IN_PROGRESS("진행중"),
    COMPLETED("완료")

    ;
    private final String category;
}
