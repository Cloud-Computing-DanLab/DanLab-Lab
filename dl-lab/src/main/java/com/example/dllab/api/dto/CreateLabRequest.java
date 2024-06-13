package com.example.dllab.api.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record CreateLabRequest(
        String name,

        String info,

        String site,

        String leader,

        String contacts
) {
}
