package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.ContrLoc;
import com.oilgascs.netra.repository.ContrLocRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.oilgascs.netra.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContrLocResource REST controller.
 *
 * @see ContrLocResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class ContrLocResourceIntTest {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ContrLocRepository contrLocRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContrLocMockMvc;

    private ContrLoc contrLoc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ContrLocResource contrLocResource = new ContrLocResource(contrLocRepository);
        this.restContrLocMockMvc = MockMvcBuilders.standaloneSetup(contrLocResource)
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
    public static ContrLoc createEntity(EntityManager em) {
        ContrLoc contrLoc = new ContrLoc()
                .location(DEFAULT_LOCATION)
                .updater(DEFAULT_UPDATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return contrLoc;
    }

    @Before
    public void initTest() {
        contrLoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrLoc() throws Exception {
        int databaseSizeBeforeCreate = contrLocRepository.findAll().size();

        // Create the ContrLoc

        restContrLocMockMvc.perform(post("/api/contr-locs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrLoc)))
            .andExpect(status().isCreated());

        // Validate the ContrLoc in the database
        List<ContrLoc> contrLocList = contrLocRepository.findAll();
        assertThat(contrLocList).hasSize(databaseSizeBeforeCreate + 1);
        ContrLoc testContrLoc = contrLocList.get(contrLocList.size() - 1);
        assertThat(testContrLoc.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testContrLoc.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testContrLoc.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createContrLocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contrLocRepository.findAll().size();

        // Create the ContrLoc with an existing ID
        ContrLoc existingContrLoc = new ContrLoc();
        existingContrLoc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContrLocMockMvc.perform(post("/api/contr-locs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingContrLoc)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContrLoc> contrLocList = contrLocRepository.findAll();
        assertThat(contrLocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContrLocs() throws Exception {
        // Initialize the database
        contrLocRepository.saveAndFlush(contrLoc);

        // Get all the contrLocList
        restContrLocMockMvc.perform(get("/api/contr-locs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrLoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getContrLoc() throws Exception {
        // Initialize the database
        contrLocRepository.saveAndFlush(contrLoc);

        // Get the contrLoc
        restContrLocMockMvc.perform(get("/api/contr-locs/{id}", contrLoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contrLoc.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingContrLoc() throws Exception {
        // Get the contrLoc
        restContrLocMockMvc.perform(get("/api/contr-locs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrLoc() throws Exception {
        // Initialize the database
        contrLocRepository.saveAndFlush(contrLoc);
        int databaseSizeBeforeUpdate = contrLocRepository.findAll().size();

        // Update the contrLoc
        ContrLoc updatedContrLoc = contrLocRepository.findOne(contrLoc.getId());
        updatedContrLoc
                .location(UPDATED_LOCATION)
                .updater(UPDATED_UPDATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restContrLocMockMvc.perform(put("/api/contr-locs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrLoc)))
            .andExpect(status().isOk());

        // Validate the ContrLoc in the database
        List<ContrLoc> contrLocList = contrLocRepository.findAll();
        assertThat(contrLocList).hasSize(databaseSizeBeforeUpdate);
        ContrLoc testContrLoc = contrLocList.get(contrLocList.size() - 1);
        assertThat(testContrLoc.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testContrLoc.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testContrLoc.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingContrLoc() throws Exception {
        int databaseSizeBeforeUpdate = contrLocRepository.findAll().size();

        // Create the ContrLoc

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContrLocMockMvc.perform(put("/api/contr-locs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contrLoc)))
            .andExpect(status().isCreated());

        // Validate the ContrLoc in the database
        List<ContrLoc> contrLocList = contrLocRepository.findAll();
        assertThat(contrLocList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContrLoc() throws Exception {
        // Initialize the database
        contrLocRepository.saveAndFlush(contrLoc);
        int databaseSizeBeforeDelete = contrLocRepository.findAll().size();

        // Get the contrLoc
        restContrLocMockMvc.perform(delete("/api/contr-locs/{id}", contrLoc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContrLoc> contrLocList = contrLocRepository.findAll();
        assertThat(contrLocList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContrLoc.class);
    }
}
