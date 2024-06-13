package com.example.dllab.api.service;

import com.example.dllab.IntegrationHelper;
import com.example.dllab.api.dto.CreateLabEventRequest;
import com.example.dllab.api.dto.LabEventResponse;
import com.example.dllab.api.dto.UpdateLabEventRequest;
import com.example.dllab.common.exception.ExceptionMessage;
import com.example.dllab.common.exception.handler.LabEventException;
import com.example.dllab.domain.event.LabEvent;
import com.example.dllab.domain.event.constant.LabEventCategory;
import com.example.dllab.domain.event.constant.LabEventStatus;
import com.example.dllab.domain.event.repository.LabEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.dllab.IntegrationHelper.NON_ASCII;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(NON_ASCII)
@Transactional
class LabEventServiceTest extends IntegrationHelper {
    @Autowired
    private LabEventService labEventService;

    @Autowired
    private LabEventRepository labEventRepository;

    @BeforeEach
    void setUp() {
        labEventRepository.saveAll(List.of(
                LabEvent.builder().labId(1L).memberId(1L).title("Event1").detail("Detail1").status(LabEventStatus.IN_PROGRESS).build(),
                LabEvent.builder().labId(1L).memberId(1L).title("Event3").detail("Detail3").status(LabEventStatus.IN_PROGRESS).build(),
                LabEvent.builder().labId(1L).memberId(1L).title("Event2").detail("Detail2").status(LabEventStatus.IN_PROGRESS).build()
        ));
    }

    @Test
    void 전체_연구실_이벤트_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<LabEvent> labEventPage = labEventService.getAllLabEvents(pageable);

        // then
        assertNotNull(labEventPage);
        assertEquals(3, labEventPage.getTotalElements());

        List<LabEvent> labEvents = labEventPage.getContent();
        assertTrue(labEvents.get(0).getId() > labEvents.get(1).getId());
        assertTrue(labEvents.get(1).getId() > labEvents.get(2).getId());
    }

    @Test
    void 특정_연구실_이벤트_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<LabEvent> labEventPage = labEventService.getLabEventsByLabId(1L, pageable);

        // then
        assertNotNull(labEventPage);
        assertEquals(3, labEventPage.getTotalElements());
    }

    @Test
    void 특정_연구실_특정_이벤트_상세_조회() {
        // given
        LabEvent labEvent = labEventRepository.findByTitle("Event1").orElseThrow();

        // when
        LabEventResponse labEventResponse = labEventService.getLabEvent(labEvent.getId());

        // then
        assertNotNull(labEventResponse);
        assertEquals("Event1", labEventResponse.title());
    }

    @Test
    void 존재하지_않는_연구실_이벤트_상세_조회시_예외발생() {
        // given
        Long nonExistentEventId = 999L;

        // when & then
        LabEventException exception = assertThrows(LabEventException.class, () -> labEventService.getLabEvent(nonExistentEventId));
        assertEquals(ExceptionMessage.LAB_EVENT_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void 연구실_이벤트_생성() {
        // given
        CreateLabEventRequest request = new CreateLabEventRequest(1L,
                1L,
                "testTitle",
                "testDetail",
                LabEventStatus.IN_PROGRESS,
                LabEventCategory.EVENT);

        // when
        labEventService.createLabEvent(request);

        // then
        LabEvent labEvent = labEventRepository.findByTitle("testTitle").get();
        assertEquals(labEvent.getLabId(), 1L);
        assertEquals(labEvent.getMemberId(), 1L);
        assertEquals(labEvent.getStatus(), LabEventStatus.IN_PROGRESS);
    }

    @Test
    void 연구실_이벤트_수정() {
        // given
        LabEvent labEvent = labEventRepository.findByTitle("Event1").orElseThrow();
        UpdateLabEventRequest request = new UpdateLabEventRequest("UpdatedEvent1", "UpdatedDetail1", LabEventStatus.COMPLETED);

        // when
        labEventService.updateLabEvent(labEvent.getId(), request);

        // then
        LabEvent updatedLabEvent = labEventRepository.findById(labEvent.getId()).orElseThrow();
        assertEquals("UpdatedEvent1", updatedLabEvent.getTitle());
        assertEquals("UpdatedDetail1", updatedLabEvent.getDetail());
        assertEquals(LabEventStatus.COMPLETED, updatedLabEvent.getStatus());
    }
}
