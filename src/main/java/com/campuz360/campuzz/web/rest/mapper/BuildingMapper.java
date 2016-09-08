package com.campuz360.campuzz.web.rest.mapper;

import com.campuz360.campuzz.domain.*;
import com.campuz360.campuzz.web.rest.dto.BuildingDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Building and its DTO BuildingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuildingMapper {

    BuildingDTO buildingToBuildingDTO(Building building);

    List<BuildingDTO> buildingsToBuildingDTOs(List<Building> buildings);

    Building buildingDTOToBuilding(BuildingDTO buildingDTO);

    List<Building> buildingDTOsToBuildings(List<BuildingDTO> buildingDTOs);
}
