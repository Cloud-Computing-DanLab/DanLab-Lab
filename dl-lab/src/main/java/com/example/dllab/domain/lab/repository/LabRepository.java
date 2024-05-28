package com.example.dllab.domain.lab.repository;

import com.example.dllab.domain.lab.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long>, JpaSpecificationExecutor<Lab> {
    Optional<Lab> findByName(String name);
}
