package com.tw.ai.repository;

import com.tw.ai.model.AiLocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiLocationsRepository extends JpaRepository<AiLocations, AiLocations.LocationPK> {
}