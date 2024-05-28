package com.example.dllab.api.service;

import com.example.dllab.IntegrationHelper;
import com.example.dllab.api.dto.LabInfoResponse;
import com.example.dllab.api.dto.UpdateLabInfoRequest;
import com.example.dllab.common.exception.ExceptionMessage;
import com.example.dllab.common.exception.handler.LabException;
import com.example.dllab.domain.lab.Lab;
import com.example.dllab.domain.lab.repository.LabRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.example.dllab.IntegrationHelper.NON_ASCII;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings(NON_ASCII)
class LabServiceTest extends IntegrationHelper {
    @Autowired
    private LabService labService;

    @Autowired
    private LabRepository labRepository;

    @BeforeEach
    void setUp() {
        labRepository.saveAll(List.of(
                Lab.builder().name("L1").info("Info1").leader("Leader1").contacts("Contacts1").build(),
                Lab.builder().name("L2").info("Info2").leader("Leader2").contacts("Contacts2").build(),
                Lab.builder().name("L3").info("Info3").leader("Leader3").contacts("Contacts3").build()
        ));
    }

    @Test
    void 연구실_프로필_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

        // when
        Page<Lab> labPage = labService.getLabInfoList(pageable, null);

        // then
        assertNotNull(labPage);
        assertEquals(3, labPage.getTotalElements());

        List<Lab> labs = labPage.getContent();
        assertEquals("L3", labs.get(0).getName());
        assertEquals("L2", labs.get(1).getName());
        assertEquals("L1", labs.get(2).getName());
    }

    @Test
    void 검색_키워드로_연구실_프로필_리스트_조회() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        String searchKeyword = "L1";

        // when
        Page<Lab> labPage = labService.getLabInfoList(pageable, searchKeyword);

        // then
        assertNotNull(labPage);
        assertEquals(1, labPage.getTotalElements());
    }

    @Test
    void 연구실_프로필_상세_조회() {
        // given
        Lab lab = labRepository.findByName("L1").get();

        // when
        LabInfoResponse labInfoResponse = labService.getLabInfo(lab.getId());

        // then
        assertNotNull(labInfoResponse);
        assertEquals("L1", labInfoResponse.name());
    }

    @Test
    void 존재하지_않는_연구실_프로필_상세_조회시_예외발생() {
        // given
        Long nonExistentLabId = 999L;

        // when & then
        LabException exception = assertThrows(LabException.class, () -> labService.getLabInfo(nonExistentLabId));
        assertEquals(ExceptionMessage.LAB_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void 연구실_프로필_수정() {
        // given
        Lab lab = labRepository.findByName("L1").get();
        UpdateLabInfoRequest request = new UpdateLabInfoRequest("UpdatedL1", "UpdatedInfo1", "UpdatedContacts1");

        // when
        labService.updateLabInfo(lab.getId(), request);

        // then
        Lab updatedLab = labRepository.findById(lab.getId()).orElseThrow();
        assertEquals("UpdatedL1", updatedLab.getName());
        assertEquals("UpdatedInfo1", updatedLab.getInfo());
        assertEquals("UpdatedContacts1", updatedLab.getContacts());
    }

}