package com.campuz360.campuzz.repository;

import com.campuz360.campuzz.domain.Building;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Building entity.
 */
@SuppressWarnings("unused")
public interface BuildingRepository extends JpaRepository<Building,Long> {

}
