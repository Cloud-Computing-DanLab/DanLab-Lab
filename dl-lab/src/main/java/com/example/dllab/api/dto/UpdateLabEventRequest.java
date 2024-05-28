package com.example.dllab.api.dto;

import com.example.dllab.domain.event.constant.LabEventStatus;
import lombok.Builder;

@Builder
public record UpdateLabEventRequest(
        String title,
        String detail,
        LabEventStatus status

) {
}
