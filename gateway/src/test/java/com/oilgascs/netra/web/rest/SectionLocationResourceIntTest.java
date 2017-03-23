package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.SectionLocation;
import com.oilgascs.netra.repository.SectionLocationRepository;
import com.oilgascs.netra.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SectionLocationResource REST controller.
 *
 * @see SectionLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class SectionLocationResourceIntTest {

    private static final String DEFAULT_LOCATION_NBR = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NBR = "BBBBBBBBBB";

    @Autowired
    private SectionLocationRepository sectionLocationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionLocationMockMvc;

    private SectionLocation sectionLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SectionLocationResource sectionLocationResource = new SectionLocationResource(sectionLocationRepository);
        this.restSectionLocationMockMvc = MockMvcBuilders.standaloneSetup(sectionLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SectionLocation createEntity(EntityManager em) {
        SectionLocation sectionLocation = new SectionLocation()
                .locationNbr(DEFAULT_LOCATION_NBR);
        return sectionLocation;
    }

    @Before
    public void initTest() {
        sectionLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSectionLocation() throws Exception {
        int databaseSizeBeforeCreate = sectionLocationRepository.findAll().size();

        // Create the SectionLocation

        restSectionLocationMockMvc.perform(post("/api/section-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionLocation)))
            .andExpect(status().isCreated());

        // Validate the SectionLocation in the database
        List<SectionLocation> sectionLocationList = sectionLocationRepository.findAll();
        assertThat(sectionLocationList).hasSize(databaseSizeBeforeCreate + 1);
        SectionLocation testSectionLocation = sectionLocationList.get(sectionLocationList.size() - 1);
        assertThat(testSectionLocation.getLocationNbr()).isEqualTo(DEFAULT_LOCATION_NBR);
    }

    @Test
    @Transactional
    public void createSectionLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionLocationRepository.findAll().size();

        // Create the SectionLocation with an existing ID
        SectionLocation existingSectionLocation = new SectionLocation();
        existingSectionLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionLocationMockMvc.perform(post("/api/section-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSectionLocation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SectionLocation> sectionLocationList = sectionLocationRepository.findAll();
        assertThat(sectionLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSectionLocations() throws Exception {
        // Initialize the database
        sectionLocationRepository.saveAndFlush(sectionLocation);

        // Get all the sectionLocationList
        restSectionLocationMockMvc.perform(get("/api/section-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sectionLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationNbr").value(hasItem(DEFAULT_LOCATION_NBR.toString())));
    }

    @Test
    @Transactional
    public void getSectionLocation() throws Exception {
        // Initialize the database
        sectionLocationRepository.saveAndFlush(sectionLocation);

        // Get the sectionLocation
        restSectionLocationMockMvc.perform(get("/api/section-locations/{id}", sectionLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sectionLocation.getId().intValue()))
            .andExpect(jsonPath("$.locationNbr").value(DEFAULT_LOCATION_NBR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSectionLocation() throws Exception {
        // Get the sectionLocation
        restSectionLocationMockMvc.perform(get("/api/section-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSectionLocation() throws Exception {
        // Initialize the database
        sectionLocationRepository.saveAndFlush(sectionLocation);
        int databaseSizeBeforeUpdate = sectionLocationRepository.findAll().size();

        // Update the sectionLocation
        SectionLocation updatedSectionLocation = sectionLocationRepository.findOne(sectionLocation.getId());
        updatedSectionLocation
                .locationNbr(UPDATED_LOCATION_NBR);

        restSectionLocationMockMvc.perform(put("/api/section-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSectionLocation)))
            .andExpect(status().isOk());

        // Validate the SectionLocation in the database
        List<SectionLocation> sectionLocationList = sectionLocationRepository.findAll();
        assertThat(sectionLocationList).hasSize(databaseSizeBeforeUpdate);
        SectionLocation testSectionLocation = sectionLocationList.get(sectionLocationList.size() - 1);
        assertThat(testSectionLocation.getLocationNbr()).isEqualTo(UPDATED_LOCATION_NBR);
    }

    @Test
    @Transactional
    public void updateNonExistingSectionLocation() throws Exception {
        int databaseSizeBeforeUpdate = sectionLocationRepository.findAll().size();

        // Create the SectionLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionLocationMockMvc.perform(put("/api/section-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionLocation)))
            .andExpect(status().isCreated());

        // Validate the SectionLocation in the database
        List<SectionLocation> sectionLocationList = sectionLocationRepository.findAll();
        assertThat(sectionLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSectionLocation() throws Exception {
        // Initialize the database
        sectionLocationRepository.saveAndFlush(sectionLocation);
        int databaseSizeBeforeDelete = sectionLocationRepository.findAll().size();

        // Get the sectionLocation
        restSectionLocationMockMvc.perform(delete("/api/section-locations/{id}", sectionLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SectionLocation> sectionLocationList = sectionLocationRepository.findAll();
        assertThat(sectionLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionLocation.class);
    }
}
