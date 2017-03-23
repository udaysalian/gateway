package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.SchedEvent;
import com.oilgascs.netra.repository.SchedEventRepository;
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
 * Test class for the SchedEventResource REST controller.
 *
 * @see SchedEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class SchedEventResourceIntTest {

    private static final Long DEFAULT_EVENT_ID = 1L;
    private static final Long UPDATED_EVENT_ID = 2L;

    private static final Integer DEFAULT_WORKING_CAPACITY = 1;
    private static final Integer UPDATED_WORKING_CAPACITY = 2;

    private static final Integer DEFAULT_ADJ_WORKING_CAPACITY = 1;
    private static final Integer UPDATED_ADJ_WORKING_CAPACITY = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SchedEventRepository schedEventRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchedEventMockMvc;

    private SchedEvent schedEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchedEventResource schedEventResource = new SchedEventResource(schedEventRepository);
        this.restSchedEventMockMvc = MockMvcBuilders.standaloneSetup(schedEventResource)
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
    public static SchedEvent createEntity(EntityManager em) {
        SchedEvent schedEvent = new SchedEvent()
                .eventId(DEFAULT_EVENT_ID)
                .workingCapacity(DEFAULT_WORKING_CAPACITY)
                .adjWorkingCapacity(DEFAULT_ADJ_WORKING_CAPACITY)
                .status(DEFAULT_STATUS)
                .eventType(DEFAULT_EVENT_TYPE)
                .updater(DEFAULT_UPDATER)
                .updateTimestamp(DEFAULT_UPDATE_TIMESTAMP);
        return schedEvent;
    }

    @Before
    public void initTest() {
        schedEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedEvent() throws Exception {
        int databaseSizeBeforeCreate = schedEventRepository.findAll().size();

        // Create the SchedEvent

        restSchedEventMockMvc.perform(post("/api/sched-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedEvent)))
            .andExpect(status().isCreated());

        // Validate the SchedEvent in the database
        List<SchedEvent> schedEventList = schedEventRepository.findAll();
        assertThat(schedEventList).hasSize(databaseSizeBeforeCreate + 1);
        SchedEvent testSchedEvent = schedEventList.get(schedEventList.size() - 1);
        assertThat(testSchedEvent.getEventId()).isEqualTo(DEFAULT_EVENT_ID);
        assertThat(testSchedEvent.getWorkingCapacity()).isEqualTo(DEFAULT_WORKING_CAPACITY);
        assertThat(testSchedEvent.getAdjWorkingCapacity()).isEqualTo(DEFAULT_ADJ_WORKING_CAPACITY);
        assertThat(testSchedEvent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSchedEvent.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testSchedEvent.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testSchedEvent.getUpdateTimestamp()).isEqualTo(DEFAULT_UPDATE_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createSchedEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schedEventRepository.findAll().size();

        // Create the SchedEvent with an existing ID
        SchedEvent existingSchedEvent = new SchedEvent();
        existingSchedEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedEventMockMvc.perform(post("/api/sched-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchedEvent)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchedEvent> schedEventList = schedEventRepository.findAll();
        assertThat(schedEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchedEvents() throws Exception {
        // Initialize the database
        schedEventRepository.saveAndFlush(schedEvent);

        // Get all the schedEventList
        restSchedEventMockMvc.perform(get("/api/sched-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventId").value(hasItem(DEFAULT_EVENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].workingCapacity").value(hasItem(DEFAULT_WORKING_CAPACITY)))
            .andExpect(jsonPath("$.[*].adjWorkingCapacity").value(hasItem(DEFAULT_ADJ_WORKING_CAPACITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimestamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void getSchedEvent() throws Exception {
        // Initialize the database
        schedEventRepository.saveAndFlush(schedEvent);

        // Get the schedEvent
        restSchedEventMockMvc.perform(get("/api/sched-events/{id}", schedEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schedEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventId").value(DEFAULT_EVENT_ID.intValue()))
            .andExpect(jsonPath("$.workingCapacity").value(DEFAULT_WORKING_CAPACITY))
            .andExpect(jsonPath("$.adjWorkingCapacity").value(DEFAULT_ADJ_WORKING_CAPACITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimestamp").value(sameInstant(DEFAULT_UPDATE_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingSchedEvent() throws Exception {
        // Get the schedEvent
        restSchedEventMockMvc.perform(get("/api/sched-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedEvent() throws Exception {
        // Initialize the database
        schedEventRepository.saveAndFlush(schedEvent);
        int databaseSizeBeforeUpdate = schedEventRepository.findAll().size();

        // Update the schedEvent
        SchedEvent updatedSchedEvent = schedEventRepository.findOne(schedEvent.getId());
        updatedSchedEvent
                .eventId(UPDATED_EVENT_ID)
                .workingCapacity(UPDATED_WORKING_CAPACITY)
                .adjWorkingCapacity(UPDATED_ADJ_WORKING_CAPACITY)
                .status(UPDATED_STATUS)
                .eventType(UPDATED_EVENT_TYPE)
                .updater(UPDATED_UPDATER)
                .updateTimestamp(UPDATED_UPDATE_TIMESTAMP);

        restSchedEventMockMvc.perform(put("/api/sched-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchedEvent)))
            .andExpect(status().isOk());

        // Validate the SchedEvent in the database
        List<SchedEvent> schedEventList = schedEventRepository.findAll();
        assertThat(schedEventList).hasSize(databaseSizeBeforeUpdate);
        SchedEvent testSchedEvent = schedEventList.get(schedEventList.size() - 1);
        assertThat(testSchedEvent.getEventId()).isEqualTo(UPDATED_EVENT_ID);
        assertThat(testSchedEvent.getWorkingCapacity()).isEqualTo(UPDATED_WORKING_CAPACITY);
        assertThat(testSchedEvent.getAdjWorkingCapacity()).isEqualTo(UPDATED_ADJ_WORKING_CAPACITY);
        assertThat(testSchedEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSchedEvent.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testSchedEvent.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testSchedEvent.getUpdateTimestamp()).isEqualTo(UPDATED_UPDATE_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedEvent() throws Exception {
        int databaseSizeBeforeUpdate = schedEventRepository.findAll().size();

        // Create the SchedEvent

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchedEventMockMvc.perform(put("/api/sched-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedEvent)))
            .andExpect(status().isCreated());

        // Validate the SchedEvent in the database
        List<SchedEvent> schedEventList = schedEventRepository.findAll();
        assertThat(schedEventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchedEvent() throws Exception {
        // Initialize the database
        schedEventRepository.saveAndFlush(schedEvent);
        int databaseSizeBeforeDelete = schedEventRepository.findAll().size();

        // Get the schedEvent
        restSchedEventMockMvc.perform(delete("/api/sched-events/{id}", schedEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchedEvent> schedEventList = schedEventRepository.findAll();
        assertThat(schedEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedEvent.class);
    }
}
