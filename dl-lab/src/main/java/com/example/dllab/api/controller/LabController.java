package com.example.dllab.api.controller;

import com.example.dllab.api.dto.UpdateLabInfoRequest;
import com.example.dllab.api.service.LabService;
import com.example.dllab.common.response.JsonResult;
import com.example.dllab.domain.lab.Lab;
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
@RequestMapping("/lab")
@RequiredArgsConstructor
public class LabController {
    private final LabService labService;
    // 연구실 프로필 리스트 조회(검색)
    @GetMapping("/")
    public JsonResult<?> getLabInfoList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(name = "searchKeyword", required = false) String searchKeyword) {

        // 정렬 방식 설정
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);

        // 페이지 설정
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // 페이지 조회
        Page<Lab> labPage = labService.getLabInfoList(pageable, searchKeyword);

        return JsonResult.successOf(labPage);
    }

    // 연구실 프로필 상세 조회
    @GetMapping("/{labId}")
    public JsonResult<?> getLabInfo(@PathVariable(name = "labId") Long labId) {
        return JsonResult.successOf(labService.getLabInfo(labId));
    }

    // 연구실 프로필 수정
    @PostMapping("/{labId}/update")
    public JsonResult<?> updateLab(@PathVariable(name = "labId") Long labId,
                                   @Valid @RequestBody UpdateLabInfoRequest request) {

        labService.updateLabInfo(labId, request);
        return JsonResult.successOf("연구실 프로필 수정이 완료되었습니다.");
    }
}
