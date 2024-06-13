package com.example.dllab.api.dto;

import com.example.dllab.domain.lab.Lab;
import lombok.Builder;

@Builder
public record LabInfoResponse(
        Long labId,
        String name,
        String info,

        String site,
        String leader,
        String contacts

) {
    public static LabInfoResponse of(Lab lab) {
        return LabInfoResponse.builder()
                .labId(lab.getId())
                .name(lab.getName())
                .info(lab.getInfo())
                .site(lab.getSite())
                .leader(lab.getLeader())
                .contacts(lab.getContacts())
                .build();
    }
}
