package com.example.dllab.api.service;

import com.example.dllab.api.dto.CreateLabRequest;
import com.example.dllab.api.dto.LabInfoResponse;
import com.example.dllab.api.dto.UpdateLabInfoRequest;
import com.example.dllab.common.exception.ExceptionMessage;
import com.example.dllab.common.exception.handler.LabException;
import com.example.dllab.domain.lab.Lab;
import com.example.dllab.domain.lab.repository.LabRepository;
import com.example.dllab.domain.lab.repository.LabSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabService {

    private final LabRepository labRepository;

    public Page<Lab> getLabInfoList(Pageable pageable, String searchKeyword) {
        Specification<Lab> spec = Specification.where(null);

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            spec = spec.and(LabSpecification.hasName(searchKeyword)
                    .or(LabSpecification.hasLeader(searchKeyword)));
        }

        return labRepository.findAll(spec, pageable);
    }

    public LabInfoResponse getLabInfo(Long labId) {
        Lab lab = labRepository.findById(labId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.LAB_NOT_FOUND.getText(), labId);
            throw new LabException(ExceptionMessage.LAB_NOT_FOUND);
        });

        return LabInfoResponse.of(lab);
    }

    @Transactional
    public void createLab(CreateLabRequest request) {
        labRepository.save(
                Lab.builder()
                        .name(request.name())
                        .leader(request.leader())
                        .info(request.info())
                        .contacts(request.contacts())
                        .site(request.site())
                        .build()
        );
    }

    @Transactional
    public void updateLabInfo(Long labId, UpdateLabInfoRequest request) {
        Lab lab = labRepository.findById(labId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.LAB_NOT_FOUND.getText(), labId);
            throw new LabException(ExceptionMessage.LAB_NOT_FOUND);
        });

        lab.updateLab(request.name(), request.info(), request.contacts(), request.site());
    }

}
