package com.campuz360.campuzz.service;

import com.campuz360.campuzz.domain.Building;
import com.campuz360.campuzz.web.rest.dto.BuildingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Building.
 */
public interface BuildingService {

    /**
     * Save a building.
     * 
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    BuildingDTO save(BuildingDTO buildingDTO);

    /**
     *  Get all the buildings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Building> findAll(Pageable pageable);

    /**
     *  Get the "id" building.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    BuildingDTO findOne(Long id);

    /**
     *  Delete the "id" building.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the building corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Building> search(String query, Pageable pageable);
}
