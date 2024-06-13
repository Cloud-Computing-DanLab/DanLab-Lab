package com.example.dllab.api.dto;

import com.example.dllab.domain.event.constant.LabEventCategory;
import com.example.dllab.domain.event.constant.LabEventStatus;
import lombok.Builder;

@Builder
public record CreateLabEventRequest(
        Long labId,
        Long memberId,
        String title,
        String detail,
        LabEventStatus status,
        LabEventCategory category
) {
}