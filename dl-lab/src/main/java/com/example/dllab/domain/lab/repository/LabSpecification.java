package com.example.dllab.domain.lab.repository;

import com.example.dllab.domain.lab.Lab;
import org.springframework.data.jpa.domain.Specification;

public class LabSpecification {

    public static Specification<Lab> hasName(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Lab> hasLeader(String leader) {
        return (root, query, cb) -> cb.like(root.get("leader"), "%" + leader + "%");
    }
}

