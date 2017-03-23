package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.RtSched;
import com.oilgascs.netra.repository.RtSchedRepository;
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
 * Test class for the RtSchedResource REST controller.
 *
 * @see RtSchedResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class RtSchedResourceIntTest {

    private static final String DEFAULT_RS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RtSchedRepository rtSchedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRtSchedMockMvc;

    private RtSched rtSched;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            RtSchedResource rtSchedResource = new RtSchedResource(rtSchedRepository);
        this.restRtSchedMockMvc = MockMvcBuilders.standaloneSetup(rtSchedResource)
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
    public static RtSched createEntity(EntityManager em) {
        RtSched rtSched = new RtSched()
                .rsType(DEFAULT_RS_TYPE)
                .updater(DEFAULT_UPDATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return rtSched;
    }

    @Before
    public void initTest() {
        rtSched = createEntity(em);
    }

    @Test
    @Transactional
    public void createRtSched() throws Exception {
        int databaseSizeBeforeCreate = rtSchedRepository.findAll().size();

        // Create the RtSched

        restRtSchedMockMvc.perform(post("/api/rt-scheds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rtSched)))
            .andExpect(status().isCreated());

        // Validate the RtSched in the database
        List<RtSched> rtSchedList = rtSchedRepository.findAll();
        assertThat(rtSchedList).hasSize(databaseSizeBeforeCreate + 1);
        RtSched testRtSched = rtSchedList.get(rtSchedList.size() - 1);
        assertThat(testRtSched.getRsType()).isEqualTo(DEFAULT_RS_TYPE);
        assertThat(testRtSched.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testRtSched.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createRtSchedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rtSchedRepository.findAll().size();

        // Create the RtSched with an existing ID
        RtSched existingRtSched = new RtSched();
        existingRtSched.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRtSchedMockMvc.perform(post("/api/rt-scheds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRtSched)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RtSched> rtSchedList = rtSchedRepository.findAll();
        assertThat(rtSchedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRtScheds() throws Exception {
        // Initialize the database
        rtSchedRepository.saveAndFlush(rtSched);

        // Get all the rtSchedList
        restRtSchedMockMvc.perform(get("/api/rt-scheds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rtSched.getId().intValue())))
            .andExpect(jsonPath("$.[*].rsType").value(hasItem(DEFAULT_RS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getRtSched() throws Exception {
        // Initialize the database
        rtSchedRepository.saveAndFlush(rtSched);

        // Get the rtSched
        restRtSchedMockMvc.perform(get("/api/rt-scheds/{id}", rtSched.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rtSched.getId().intValue()))
            .andExpect(jsonPath("$.rsType").value(DEFAULT_RS_TYPE.toString()))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingRtSched() throws Exception {
        // Get the rtSched
        restRtSchedMockMvc.perform(get("/api/rt-scheds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRtSched() throws Exception {
        // Initialize the database
        rtSchedRepository.saveAndFlush(rtSched);
        int databaseSizeBeforeUpdate = rtSchedRepository.findAll().size();

        // Update the rtSched
        RtSched updatedRtSched = rtSchedRepository.findOne(rtSched.getId());
        updatedRtSched
                .rsType(UPDATED_RS_TYPE)
                .updater(UPDATED_UPDATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restRtSchedMockMvc.perform(put("/api/rt-scheds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRtSched)))
            .andExpect(status().isOk());

        // Validate the RtSched in the database
        List<RtSched> rtSchedList = rtSchedRepository.findAll();
        assertThat(rtSchedList).hasSize(databaseSizeBeforeUpdate);
        RtSched testRtSched = rtSchedList.get(rtSchedList.size() - 1);
        assertThat(testRtSched.getRsType()).isEqualTo(UPDATED_RS_TYPE);
        assertThat(testRtSched.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testRtSched.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingRtSched() throws Exception {
        int databaseSizeBeforeUpdate = rtSchedRepository.findAll().size();

        // Create the RtSched

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRtSchedMockMvc.perform(put("/api/rt-scheds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rtSched)))
            .andExpect(status().isCreated());

        // Validate the RtSched in the database
        List<RtSched> rtSchedList = rtSchedRepository.findAll();
        assertThat(rtSchedList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRtSched() throws Exception {
        // Initialize the database
        rtSchedRepository.saveAndFlush(rtSched);
        int databaseSizeBeforeDelete = rtSchedRepository.findAll().size();

        // Get the rtSched
        restRtSchedMockMvc.perform(delete("/api/rt-scheds/{id}", rtSched.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RtSched> rtSchedList = rtSchedRepository.findAll();
        assertThat(rtSchedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RtSched.class);
    }
}
