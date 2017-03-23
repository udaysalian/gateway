package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.SchedGrp;
import com.oilgascs.netra.repository.SchedGrpRepository;
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
 * Test class for the SchedGrpResource REST controller.
 *
 * @see SchedGrpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class SchedGrpResourceIntTest {

    private static final Long DEFAULT_SCHED_GRP_ID = 1L;
    private static final Long UPDATED_SCHED_GRP_ID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SchedGrpRepository schedGrpRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchedGrpMockMvc;

    private SchedGrp schedGrp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchedGrpResource schedGrpResource = new SchedGrpResource(schedGrpRepository);
        this.restSchedGrpMockMvc = MockMvcBuilders.standaloneSetup(schedGrpResource)
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
    public static SchedGrp createEntity(EntityManager em) {
        SchedGrp schedGrp = new SchedGrp()
                .schedGrpId(DEFAULT_SCHED_GRP_ID)
                .description(DEFAULT_DESCRIPTION);
        return schedGrp;
    }

    @Before
    public void initTest() {
        schedGrp = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedGrp() throws Exception {
        int databaseSizeBeforeCreate = schedGrpRepository.findAll().size();

        // Create the SchedGrp

        restSchedGrpMockMvc.perform(post("/api/sched-grps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedGrp)))
            .andExpect(status().isCreated());

        // Validate the SchedGrp in the database
        List<SchedGrp> schedGrpList = schedGrpRepository.findAll();
        assertThat(schedGrpList).hasSize(databaseSizeBeforeCreate + 1);
        SchedGrp testSchedGrp = schedGrpList.get(schedGrpList.size() - 1);
        assertThat(testSchedGrp.getSchedGrpId()).isEqualTo(DEFAULT_SCHED_GRP_ID);
        assertThat(testSchedGrp.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSchedGrpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schedGrpRepository.findAll().size();

        // Create the SchedGrp with an existing ID
        SchedGrp existingSchedGrp = new SchedGrp();
        existingSchedGrp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedGrpMockMvc.perform(post("/api/sched-grps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchedGrp)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchedGrp> schedGrpList = schedGrpRepository.findAll();
        assertThat(schedGrpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchedGrps() throws Exception {
        // Initialize the database
        schedGrpRepository.saveAndFlush(schedGrp);

        // Get all the schedGrpList
        restSchedGrpMockMvc.perform(get("/api/sched-grps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedGrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].schedGrpId").value(hasItem(DEFAULT_SCHED_GRP_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSchedGrp() throws Exception {
        // Initialize the database
        schedGrpRepository.saveAndFlush(schedGrp);

        // Get the schedGrp
        restSchedGrpMockMvc.perform(get("/api/sched-grps/{id}", schedGrp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schedGrp.getId().intValue()))
            .andExpect(jsonPath("$.schedGrpId").value(DEFAULT_SCHED_GRP_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchedGrp() throws Exception {
        // Get the schedGrp
        restSchedGrpMockMvc.perform(get("/api/sched-grps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedGrp() throws Exception {
        // Initialize the database
        schedGrpRepository.saveAndFlush(schedGrp);
        int databaseSizeBeforeUpdate = schedGrpRepository.findAll().size();

        // Update the schedGrp
        SchedGrp updatedSchedGrp = schedGrpRepository.findOne(schedGrp.getId());
        updatedSchedGrp
                .schedGrpId(UPDATED_SCHED_GRP_ID)
                .description(UPDATED_DESCRIPTION);

        restSchedGrpMockMvc.perform(put("/api/sched-grps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchedGrp)))
            .andExpect(status().isOk());

        // Validate the SchedGrp in the database
        List<SchedGrp> schedGrpList = schedGrpRepository.findAll();
        assertThat(schedGrpList).hasSize(databaseSizeBeforeUpdate);
        SchedGrp testSchedGrp = schedGrpList.get(schedGrpList.size() - 1);
        assertThat(testSchedGrp.getSchedGrpId()).isEqualTo(UPDATED_SCHED_GRP_ID);
        assertThat(testSchedGrp.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedGrp() throws Exception {
        int databaseSizeBeforeUpdate = schedGrpRepository.findAll().size();

        // Create the SchedGrp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchedGrpMockMvc.perform(put("/api/sched-grps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedGrp)))
            .andExpect(status().isCreated());

        // Validate the SchedGrp in the database
        List<SchedGrp> schedGrpList = schedGrpRepository.findAll();
        assertThat(schedGrpList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchedGrp() throws Exception {
        // Initialize the database
        schedGrpRepository.saveAndFlush(schedGrp);
        int databaseSizeBeforeDelete = schedGrpRepository.findAll().size();

        // Get the schedGrp
        restSchedGrpMockMvc.perform(delete("/api/sched-grps/{id}", schedGrp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchedGrp> schedGrpList = schedGrpRepository.findAll();
        assertThat(schedGrpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedGrp.class);
    }
}
