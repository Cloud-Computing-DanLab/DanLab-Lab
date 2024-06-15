package com.example.dllab.api.controller;

import com.example.dllab.api.dto.CreateLabEventRequest;
import com.example.dllab.api.dto.LabEventResponse;
import com.example.dllab.api.dto.UpdateLabEventRequest;
import com.example.dllab.api.service.LabEventService;
import com.example.dllab.common.response.JsonResult;
import com.example.dllab.domain.event.LabEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/lab/event")
@RequiredArgsConstructor
public class LabEventController {
    private final LabEventService labEventService;

    // 연구실 글 작성
    @PostMapping("/")
    public JsonResult<?> createLabEvent(@Valid @RequestBody CreateLabEventRequest request) {
        labEventService.createLabEvent(request);
        return JsonResult.successOf("연구실 글 등록이 완료되었습니다.");
    }

    // 연구실 글 리스트 조회 (전체 또는 특정 연구실)
    @GetMapping("/")
    public JsonResult<?> getLabEvents(
            @RequestParam(required = false) Long labId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<LabEvent> labEventPage;

        if (labId != null) {
            labEventPage = labEventService.getLabEventsByLabId(labId, pageable);
        } else {
            labEventPage = labEventService.getAllLabEvents(pageable);
        }

        return JsonResult.successOf(labEventPage);
    }

    // 특정 연구실 특정 글 상세 조회
    @GetMapping("/{eventId}")
    public JsonResult<?> getLabEvent(@PathVariable Long eventId) {
        LabEventResponse labEvent = labEventService.getLabEvent(eventId);
        return JsonResult.successOf(labEvent);
    }

    // 특정 연구실 특정 글 수정
    @PostMapping("/{eventId}/update")
    public JsonResult<?> updateLabEvent(@PathVariable Long eventId,
                                        @Valid @RequestBody UpdateLabEventRequest request) {
        labEventService.updateLabEvent(eventId, request);
        return JsonResult.successOf("연구실 글 수정이 완료되었습니다.");
    }
}
