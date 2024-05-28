package com.example.dllab.domain.event;

import com.example.dllab.domain.event.constant.LabEventCategory;
import com.example.dllab.domain.event.constant.LabEventStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LabEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LAB_EVENT_ID")
    private Long id;

    @Column(name = "LAB_ID", nullable = false)
    private Long labId;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "DETAIL", nullable = false)
    private String detail;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    @ColumnDefault(value = "'PROJECT'")
    private LabEventCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @ColumnDefault(value = "'IN_PROGRESS'")
    private LabEventStatus status;

    @Builder
    public LabEvent(Long labId, Long memberId, String title, String detail, LabEventCategory category, LabEventStatus status) {
        this.labId = labId;
        this.memberId = memberId;
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.status = status;
    }

    public void updateLabEvent(String title, String detail, LabEventStatus status) {
        this.title = title;
        this.detail = detail;
        this.status = status;
    }
}
