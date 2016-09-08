package com.campuz360.campuzz.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.campuz360.campuzz.domain.Building;
import com.campuz360.campuzz.service.BuildingService;
import com.campuz360.campuzz.web.rest.util.HeaderUtil;
import com.campuz360.campuzz.web.rest.util.PaginationUtil;
import com.campuz360.campuzz.web.rest.dto.BuildingDTO;
import com.campuz360.campuzz.web.rest.mapper.BuildingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);
        
    @Inject
    private BuildingService buildingService;
    
    @Inject
    private BuildingMapper buildingMapper;
    
    /**
     * POST  /buildings : Create a new building.
     *
     * @param buildingDTO the buildingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingDTO, or with status 400 (Bad Request) if the building has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingDTO> createBuilding(@Valid @RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to save Building : {}", buildingDTO);
        if (buildingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("building", "idexists", "A new building cannot already have an ID")).body(null);
        }
        BuildingDTO result = buildingService.save(buildingDTO);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("building", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings : Updates an existing building.
     *
     * @param buildingDTO the buildingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingDTO,
     * or with status 400 (Bad Request) if the buildingDTO is not valid,
     * or with status 500 (Internal Server Error) if the buildingDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingDTO> updateBuilding(@Valid @RequestBody BuildingDTO buildingDTO) throws URISyntaxException {
        log.debug("REST request to update Building : {}", buildingDTO);
        if (buildingDTO.getId() == null) {
            return createBuilding(buildingDTO);
        }
        BuildingDTO result = buildingService.save(buildingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("building", buildingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buildings : get all the buildings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of buildings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BuildingDTO>> getAllBuildings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Buildings");
        Page<Building> page = buildingService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buildings");
        return new ResponseEntity<>(buildingMapper.buildingsToBuildingDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /buildings/:id : get the "id" building.
     *
     * @param id the id of the buildingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/buildings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BuildingDTO> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        BuildingDTO buildingDTO = buildingService.findOne(id);
        return Optional.ofNullable(buildingDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /buildings/:id : delete the "id" building.
     *
     * @param id the id of the buildingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/buildings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("building", id.toString())).build();
    }

    /**
     * SEARCH  /_search/buildings?query=:query : search for the building corresponding
     * to the query.
     *
     * @param query the query of the building search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/buildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BuildingDTO>> searchBuildings(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Buildings for query {}", query);
        Page<Building> page = buildingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/buildings");
        return new ResponseEntity<>(buildingMapper.buildingsToBuildingDTOs(page.getContent()), headers, HttpStatus.OK);
    }


}
