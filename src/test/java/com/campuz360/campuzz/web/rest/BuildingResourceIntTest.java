package com.campuz360.campuzz.web.rest;

import com.campuz360.campuzz.Campuz360App;
import com.campuz360.campuzz.domain.Building;
import com.campuz360.campuzz.repository.BuildingRepository;
import com.campuz360.campuzz.service.BuildingService;
import com.campuz360.campuzz.repository.search.BuildingSearchRepository;
import com.campuz360.campuzz.web.rest.dto.BuildingDTO;
import com.campuz360.campuzz.web.rest.mapper.BuildingMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BuildingResource REST controller.
 *
 * @see BuildingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Campuz360App.class)
@WebAppConfiguration
@IntegrationTest
public class BuildingResourceIntTest {

    private static final String DEFAULT_BUILDING_NAME = "AAAAA";
    private static final String UPDATED_BUILDING_NAME = "BBBBB";

    private static final byte[] DEFAULT_BUILDING_COVER_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BUILDING_COVER_IMAGE = TestUtil.createByteArray(3400000, "1");
    private static final String DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BUILDING_COVER_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BUILDING_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BUILDING_IMAGE_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BUILDING_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BUILDING_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BUILDING_IMAGE_2 = TestUtil.createByteArray(2100000, "1");
    private static final String DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BUILDING_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BUILDING_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BUILDING_IMAGE_3 = TestUtil.createByteArray(2100000, "1");
    private static final String DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BUILDING_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_BUILDING_ESTABLISHED_YEAR = 1;
    private static final Integer UPDATED_BUILDING_ESTABLISHED_YEAR = 2;
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    @Inject
    private BuildingRepository buildingRepository;

    @Inject
    private BuildingMapper buildingMapper;

    @Inject
    private BuildingService buildingService;

    @Inject
    private BuildingSearchRepository buildingSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBuildingMockMvc;

    private Building building;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BuildingResource buildingResource = new BuildingResource();
        ReflectionTestUtils.setField(buildingResource, "buildingService", buildingService);
        ReflectionTestUtils.setField(buildingResource, "buildingMapper", buildingMapper);
        this.restBuildingMockMvc = MockMvcBuilders.standaloneSetup(buildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        buildingSearchRepository.deleteAll();
        building = new Building();
        building.setBuildingName(DEFAULT_BUILDING_NAME);
        building.setBuildingCoverImage(DEFAULT_BUILDING_COVER_IMAGE);
        building.setBuildingCoverImageContentType(DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE);
        building.setBuildingImage1(DEFAULT_BUILDING_IMAGE_1);
        building.setBuildingImage1ContentType(DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE);
        building.setBuildingImage2(DEFAULT_BUILDING_IMAGE_2);
        building.setBuildingImage2ContentType(DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE);
        building.setBuildingImage3(DEFAULT_BUILDING_IMAGE_3);
        building.setBuildingImage3ContentType(DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE);
        building.setBuildingEstablishedYear(DEFAULT_BUILDING_ESTABLISHED_YEAR);
        building.setLocation(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createBuilding() throws Exception {
        int databaseSizeBeforeCreate = buildingRepository.findAll().size();

        // Create the Building
        BuildingDTO buildingDTO = buildingMapper.buildingToBuildingDTO(building);

        restBuildingMockMvc.perform(post("/api/buildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
                .andExpect(status().isCreated());

        // Validate the Building in the database
        List<Building> buildings = buildingRepository.findAll();
        assertThat(buildings).hasSize(databaseSizeBeforeCreate + 1);
        Building testBuilding = buildings.get(buildings.size() - 1);
        assertThat(testBuilding.getBuildingName()).isEqualTo(DEFAULT_BUILDING_NAME);
        assertThat(testBuilding.getBuildingCoverImage()).isEqualTo(DEFAULT_BUILDING_COVER_IMAGE);
        assertThat(testBuilding.getBuildingCoverImageContentType()).isEqualTo(DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage1()).isEqualTo(DEFAULT_BUILDING_IMAGE_1);
        assertThat(testBuilding.getBuildingImage1ContentType()).isEqualTo(DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage2()).isEqualTo(DEFAULT_BUILDING_IMAGE_2);
        assertThat(testBuilding.getBuildingImage2ContentType()).isEqualTo(DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage3()).isEqualTo(DEFAULT_BUILDING_IMAGE_3);
        assertThat(testBuilding.getBuildingImage3ContentType()).isEqualTo(DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingEstablishedYear()).isEqualTo(DEFAULT_BUILDING_ESTABLISHED_YEAR);
        assertThat(testBuilding.getLocation()).isEqualTo(DEFAULT_LOCATION);

        // Validate the Building in ElasticSearch
        Building buildingEs = buildingSearchRepository.findOne(testBuilding.getId());
        assertThat(buildingEs).isEqualToComparingFieldByField(testBuilding);
    }

    @Test
    @Transactional
    public void checkBuildingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildingRepository.findAll().size();
        // set the field null
        building.setBuildingName(null);

        // Create the Building, which fails.
        BuildingDTO buildingDTO = buildingMapper.buildingToBuildingDTO(building);

        restBuildingMockMvc.perform(post("/api/buildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
                .andExpect(status().isBadRequest());

        List<Building> buildings = buildingRepository.findAll();
        assertThat(buildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = buildingRepository.findAll().size();
        // set the field null
        building.setLocation(null);

        // Create the Building, which fails.
        BuildingDTO buildingDTO = buildingMapper.buildingToBuildingDTO(building);

        restBuildingMockMvc.perform(post("/api/buildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
                .andExpect(status().isBadRequest());

        List<Building> buildings = buildingRepository.findAll();
        assertThat(buildings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuildings() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get all the buildings
        restBuildingMockMvc.perform(get("/api/buildings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
                .andExpect(jsonPath("$.[*].buildingName").value(hasItem(DEFAULT_BUILDING_NAME.toString())))
                .andExpect(jsonPath("$.[*].buildingCoverImageContentType").value(hasItem(DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].buildingCoverImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_COVER_IMAGE))))
                .andExpect(jsonPath("$.[*].buildingImage1ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].buildingImage1").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_1))))
                .andExpect(jsonPath("$.[*].buildingImage2ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].buildingImage2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_2))))
                .andExpect(jsonPath("$.[*].buildingImage3ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].buildingImage3").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_3))))
                .andExpect(jsonPath("$.[*].buildingEstablishedYear").value(hasItem(DEFAULT_BUILDING_ESTABLISHED_YEAR)))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);

        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(building.getId().intValue()))
            .andExpect(jsonPath("$.buildingName").value(DEFAULT_BUILDING_NAME.toString()))
            .andExpect(jsonPath("$.buildingCoverImageContentType").value(DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.buildingCoverImage").value(Base64Utils.encodeToString(DEFAULT_BUILDING_COVER_IMAGE)))
            .andExpect(jsonPath("$.buildingImage1ContentType").value(DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.buildingImage1").value(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_1)))
            .andExpect(jsonPath("$.buildingImage2ContentType").value(DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.buildingImage2").value(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_2)))
            .andExpect(jsonPath("$.buildingImage3ContentType").value(DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.buildingImage3").value(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_3)))
            .andExpect(jsonPath("$.buildingEstablishedYear").value(DEFAULT_BUILDING_ESTABLISHED_YEAR))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuilding() throws Exception {
        // Get the building
        restBuildingMockMvc.perform(get("/api/buildings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);
        int databaseSizeBeforeUpdate = buildingRepository.findAll().size();

        // Update the building
        Building updatedBuilding = new Building();
        updatedBuilding.setId(building.getId());
        updatedBuilding.setBuildingName(UPDATED_BUILDING_NAME);
        updatedBuilding.setBuildingCoverImage(UPDATED_BUILDING_COVER_IMAGE);
        updatedBuilding.setBuildingCoverImageContentType(UPDATED_BUILDING_COVER_IMAGE_CONTENT_TYPE);
        updatedBuilding.setBuildingImage1(UPDATED_BUILDING_IMAGE_1);
        updatedBuilding.setBuildingImage1ContentType(UPDATED_BUILDING_IMAGE_1_CONTENT_TYPE);
        updatedBuilding.setBuildingImage2(UPDATED_BUILDING_IMAGE_2);
        updatedBuilding.setBuildingImage2ContentType(UPDATED_BUILDING_IMAGE_2_CONTENT_TYPE);
        updatedBuilding.setBuildingImage3(UPDATED_BUILDING_IMAGE_3);
        updatedBuilding.setBuildingImage3ContentType(UPDATED_BUILDING_IMAGE_3_CONTENT_TYPE);
        updatedBuilding.setBuildingEstablishedYear(UPDATED_BUILDING_ESTABLISHED_YEAR);
        updatedBuilding.setLocation(UPDATED_LOCATION);
        BuildingDTO buildingDTO = buildingMapper.buildingToBuildingDTO(updatedBuilding);

        restBuildingMockMvc.perform(put("/api/buildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(buildingDTO)))
                .andExpect(status().isOk());

        // Validate the Building in the database
        List<Building> buildings = buildingRepository.findAll();
        assertThat(buildings).hasSize(databaseSizeBeforeUpdate);
        Building testBuilding = buildings.get(buildings.size() - 1);
        assertThat(testBuilding.getBuildingName()).isEqualTo(UPDATED_BUILDING_NAME);
        assertThat(testBuilding.getBuildingCoverImage()).isEqualTo(UPDATED_BUILDING_COVER_IMAGE);
        assertThat(testBuilding.getBuildingCoverImageContentType()).isEqualTo(UPDATED_BUILDING_COVER_IMAGE_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage1()).isEqualTo(UPDATED_BUILDING_IMAGE_1);
        assertThat(testBuilding.getBuildingImage1ContentType()).isEqualTo(UPDATED_BUILDING_IMAGE_1_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage2()).isEqualTo(UPDATED_BUILDING_IMAGE_2);
        assertThat(testBuilding.getBuildingImage2ContentType()).isEqualTo(UPDATED_BUILDING_IMAGE_2_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingImage3()).isEqualTo(UPDATED_BUILDING_IMAGE_3);
        assertThat(testBuilding.getBuildingImage3ContentType()).isEqualTo(UPDATED_BUILDING_IMAGE_3_CONTENT_TYPE);
        assertThat(testBuilding.getBuildingEstablishedYear()).isEqualTo(UPDATED_BUILDING_ESTABLISHED_YEAR);
        assertThat(testBuilding.getLocation()).isEqualTo(UPDATED_LOCATION);

        // Validate the Building in ElasticSearch
        Building buildingEs = buildingSearchRepository.findOne(testBuilding.getId());
        assertThat(buildingEs).isEqualToComparingFieldByField(testBuilding);
    }

    @Test
    @Transactional
    public void deleteBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);
        int databaseSizeBeforeDelete = buildingRepository.findAll().size();

        // Get the building
        restBuildingMockMvc.perform(delete("/api/buildings/{id}", building.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean buildingExistsInEs = buildingSearchRepository.exists(building.getId());
        assertThat(buildingExistsInEs).isFalse();

        // Validate the database is empty
        List<Building> buildings = buildingRepository.findAll();
        assertThat(buildings).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBuilding() throws Exception {
        // Initialize the database
        buildingRepository.saveAndFlush(building);
        buildingSearchRepository.save(building);

        // Search the building
        restBuildingMockMvc.perform(get("/api/_search/buildings?query=id:" + building.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(building.getId().intValue())))
            .andExpect(jsonPath("$.[*].buildingName").value(hasItem(DEFAULT_BUILDING_NAME.toString())))
            .andExpect(jsonPath("$.[*].buildingCoverImageContentType").value(hasItem(DEFAULT_BUILDING_COVER_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].buildingCoverImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_COVER_IMAGE))))
            .andExpect(jsonPath("$.[*].buildingImage1ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].buildingImage1").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_1))))
            .andExpect(jsonPath("$.[*].buildingImage2ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].buildingImage2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_2))))
            .andExpect(jsonPath("$.[*].buildingImage3ContentType").value(hasItem(DEFAULT_BUILDING_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].buildingImage3").value(hasItem(Base64Utils.encodeToString(DEFAULT_BUILDING_IMAGE_3))))
            .andExpect(jsonPath("$.[*].buildingEstablishedYear").value(hasItem(DEFAULT_BUILDING_ESTABLISHED_YEAR)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }
}
