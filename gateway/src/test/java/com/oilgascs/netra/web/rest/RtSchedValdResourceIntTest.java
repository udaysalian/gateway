package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.RtSchedVald;
import com.oilgascs.netra.repository.RtSchedValdRepository;
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
 * Test class for the RtSchedValdResource REST controller.
 *
 * @see RtSchedValdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class RtSchedValdResourceIntTest {

    private static final String DEFAULT_VALID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALID_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RtSchedValdRepository rtSchedValdRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRtSchedValdMockMvc;

    private RtSchedVald rtSchedVald;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            RtSchedValdResource rtSchedValdResource = new RtSchedValdResource(rtSchedValdRepository);
        this.restRtSchedValdMockMvc = MockMvcBuilders.standaloneSetup(rtSchedValdResource)
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
    public static RtSchedVald createEntity(EntityManager em) {
        RtSchedVald rtSchedVald = new RtSchedVald()
                .validType(DEFAULT_VALID_TYPE)
                .updater(DEFAULT_UPDATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return rtSchedVald;
    }

    @Before
    public void initTest() {
        rtSchedVald = createEntity(em);
    }

    @Test
    @Transactional
    public void createRtSchedVald() throws Exception {
        int databaseSizeBeforeCreate = rtSchedValdRepository.findAll().size();

        // Create the RtSchedVald

        restRtSchedValdMockMvc.perform(post("/api/rt-sched-valds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rtSchedVald)))
            .andExpect(status().isCreated());

        // Validate the RtSchedVald in the database
        List<RtSchedVald> rtSchedValdList = rtSchedValdRepository.findAll();
        assertThat(rtSchedValdList).hasSize(databaseSizeBeforeCreate + 1);
        RtSchedVald testRtSchedVald = rtSchedValdList.get(rtSchedValdList.size() - 1);
        assertThat(testRtSchedVald.getValidType()).isEqualTo(DEFAULT_VALID_TYPE);
        assertThat(testRtSchedVald.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testRtSchedVald.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createRtSchedValdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rtSchedValdRepository.findAll().size();

        // Create the RtSchedVald with an existing ID
        RtSchedVald existingRtSchedVald = new RtSchedVald();
        existingRtSchedVald.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRtSchedValdMockMvc.perform(post("/api/rt-sched-valds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRtSchedVald)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RtSchedVald> rtSchedValdList = rtSchedValdRepository.findAll();
        assertThat(rtSchedValdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRtSchedValds() throws Exception {
        // Initialize the database
        rtSchedValdRepository.saveAndFlush(rtSchedVald);

        // Get all the rtSchedValdList
        restRtSchedValdMockMvc.perform(get("/api/rt-sched-valds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rtSchedVald.getId().intValue())))
            .andExpect(jsonPath("$.[*].validType").value(hasItem(DEFAULT_VALID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getRtSchedVald() throws Exception {
        // Initialize the database
        rtSchedValdRepository.saveAndFlush(rtSchedVald);

        // Get the rtSchedVald
        restRtSchedValdMockMvc.perform(get("/api/rt-sched-valds/{id}", rtSchedVald.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rtSchedVald.getId().intValue()))
            .andExpect(jsonPath("$.validType").value(DEFAULT_VALID_TYPE.toString()))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingRtSchedVald() throws Exception {
        // Get the rtSchedVald
        restRtSchedValdMockMvc.perform(get("/api/rt-sched-valds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRtSchedVald() throws Exception {
        // Initialize the database
        rtSchedValdRepository.saveAndFlush(rtSchedVald);
        int databaseSizeBeforeUpdate = rtSchedValdRepository.findAll().size();

        // Update the rtSchedVald
        RtSchedVald updatedRtSchedVald = rtSchedValdRepository.findOne(rtSchedVald.getId());
        updatedRtSchedVald
                .validType(UPDATED_VALID_TYPE)
                .updater(UPDATED_UPDATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restRtSchedValdMockMvc.perform(put("/api/rt-sched-valds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRtSchedVald)))
            .andExpect(status().isOk());

        // Validate the RtSchedVald in the database
        List<RtSchedVald> rtSchedValdList = rtSchedValdRepository.findAll();
        assertThat(rtSchedValdList).hasSize(databaseSizeBeforeUpdate);
        RtSchedVald testRtSchedVald = rtSchedValdList.get(rtSchedValdList.size() - 1);
        assertThat(testRtSchedVald.getValidType()).isEqualTo(UPDATED_VALID_TYPE);
        assertThat(testRtSchedVald.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testRtSchedVald.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingRtSchedVald() throws Exception {
        int databaseSizeBeforeUpdate = rtSchedValdRepository.findAll().size();

        // Create the RtSchedVald

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRtSchedValdMockMvc.perform(put("/api/rt-sched-valds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rtSchedVald)))
            .andExpect(status().isCreated());

        // Validate the RtSchedVald in the database
        List<RtSchedVald> rtSchedValdList = rtSchedValdRepository.findAll();
        assertThat(rtSchedValdList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRtSchedVald() throws Exception {
        // Initialize the database
        rtSchedValdRepository.saveAndFlush(rtSchedVald);
        int databaseSizeBeforeDelete = rtSchedValdRepository.findAll().size();

        // Get the rtSchedVald
        restRtSchedValdMockMvc.perform(delete("/api/rt-sched-valds/{id}", rtSchedVald.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RtSchedVald> rtSchedValdList = rtSchedValdRepository.findAll();
        assertThat(rtSchedValdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RtSchedVald.class);
    }
}
