package com.example.dllab.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    // LabException
    LAB_NOT_FOUND("연구실 정보를 찾을 수 없습니다."),

    ;
    private final String text;
}
