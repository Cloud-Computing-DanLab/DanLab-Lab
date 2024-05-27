package com.example.dllab.api.dto;

import lombok.Builder;

@Builder
public record UpdateLabInfoRequest(
        String name,
        String info,
        String contacts

) {
}
