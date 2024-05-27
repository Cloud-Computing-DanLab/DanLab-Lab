package com.example.dllab.domain.event.repository;

import com.example.dllab.domain.event.LabEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabEventRepository extends JpaRepository<LabEvent, Long> {

}
