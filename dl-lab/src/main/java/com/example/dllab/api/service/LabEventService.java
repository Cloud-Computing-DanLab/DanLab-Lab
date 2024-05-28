package com.example.dllab.api.service;

import com.example.dllab.api.dto.LabEventResponse;
import com.example.dllab.api.dto.UpdateLabEventRequest;
import com.example.dllab.common.exception.ExceptionMessage;
import com.example.dllab.common.exception.handler.LabEventException;
import com.example.dllab.domain.event.LabEvent;
import com.example.dllab.domain.event.repository.LabEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LabEventService {
    private final LabEventRepository labEventRepository;

    public Page<LabEvent> getAllLabEvents(Pageable pageable) {
        return labEventRepository.findAll(pageable);
    }

    public Page<LabEvent> getLabEventsByLabId(Long labId, Pageable pageable) {
        return labEventRepository.findAllByLabId(labId, pageable);
    }

    public LabEventResponse getLabEvent(Long eventId) {
        LabEvent labEvent = labEventRepository.findById(eventId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.LAB_EVENT_NOT_FOUND.getText(), eventId);
            throw new LabEventException(ExceptionMessage.LAB_EVENT_NOT_FOUND);
        });

        return LabEventResponse.of(labEvent);
    }

    @Transactional
    public void updateLabEvent(Long eventId, UpdateLabEventRequest request) {
        LabEvent labEvent = labEventRepository.findById(eventId).orElseThrow(() -> {
            log.warn("[DL WARN]: {} : {}", ExceptionMessage.LAB_EVENT_NOT_FOUND.getText(), eventId);
            throw new LabEventException(ExceptionMessage.LAB_EVENT_NOT_FOUND);
        });

        labEvent.updateLabEvent(request.title(), request.detail(), request.status());

    }
}
