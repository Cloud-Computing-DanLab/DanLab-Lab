package com.example.dllab.domain.lab;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LAB_ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "INFO", columnDefinition = "TEXT")
    private String info;

    @Column(name = "SITE")
    private String site;

    @Column(name = "LEADER", nullable = false, unique = true)
    private String leader;

    @Column(name = "CONTACTS")
    private String contacts;

    @Builder
    public Lab(String name, String info, String site, String leader, String contacts) {
        this.name = name;
        this.info = info;
        this.site = site;
        this.leader = leader;
        this.contacts = contacts;
    }

    public void updateLab(String name, String info, String contacts, String site) {
        this.name = name;
        this.info = info;
        this.contacts = contacts;
        this.site = site;
    }
}

