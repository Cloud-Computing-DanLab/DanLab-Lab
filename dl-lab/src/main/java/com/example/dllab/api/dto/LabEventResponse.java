package com.example.dllab.api.dto;

import com.example.dllab.domain.event.LabEvent;
import com.example.dllab.domain.event.constant.LabEventCategory;
import com.example.dllab.domain.event.constant.LabEventStatus;
import com.example.dllab.domain.lab.Lab;
import lombok.Builder;

@Builder
public record LabEventResponse(
        Long labEventId,
        Long labId,
        Long memberId,
        String title,
        String detail,
        LabEventCategory category,
        LabEventStatus status
) {
    public static LabEventResponse of(LabEvent labEvent) {
        return LabEventResponse.builder()
                .labEventId(labEvent.getId())
                .labId(labEvent.getLabId())
                .memberId(labEvent.getMemberId())
                .title(labEvent.getTitle())
                .detail(labEvent.getDetail())
                .category(labEvent.getCategory())
                .status(labEvent.getStatus())
                .build();
    }
}
