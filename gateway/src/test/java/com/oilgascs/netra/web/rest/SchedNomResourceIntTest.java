package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.SchedNom;
import com.oilgascs.netra.repository.SchedNomRepository;
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
import java.time.LocalDate;
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
 * Test class for the SchedNomResource REST controller.
 *
 * @see SchedNomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class SchedNomResourceIntTest {

    private static final String DEFAULT_CONTR_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_NBR = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NBR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GAS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GAS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_OLD_SCHD_RCPT_QTY = 1;
    private static final Integer UPDATED_OLD_SCHD_RCPT_QTY = 2;

    private static final Integer DEFAULT_NEW_SCHD_RCPT_QTY = 1;
    private static final Integer UPDATED_NEW_SCHD_RCPT_QTY = 2;

    private static final Integer DEFAULT_OLD_SCHD_DLVRY_QTY = 1;
    private static final Integer UPDATED_OLD_SCHD_DLVRY_QTY = 2;

    private static final Integer DEFAULT_NEW_SCHD_DLVRY_QTY = 1;
    private static final Integer UPDATED_NEW_SCHD_DLVRY_QTY = 2;

    private static final String DEFAULT_UPATER = "AAAAAAAAAA";
    private static final String UPDATED_UPATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SchedNomRepository schedNomRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchedNomMockMvc;

    private SchedNom schedNom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SchedNomResource schedNomResource = new SchedNomResource(schedNomRepository);
        this.restSchedNomMockMvc = MockMvcBuilders.standaloneSetup(schedNomResource)
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
    public static SchedNom createEntity(EntityManager em) {
        SchedNom schedNom = new SchedNom()
                .contrId(DEFAULT_CONTR_ID)
                .activityNbr(DEFAULT_ACTIVITY_NBR)
                .gasDate(DEFAULT_GAS_DATE)
                .oldSchdRcptQty(DEFAULT_OLD_SCHD_RCPT_QTY)
                .newSchdRcptQty(DEFAULT_NEW_SCHD_RCPT_QTY)
                .oldSchdDlvryQty(DEFAULT_OLD_SCHD_DLVRY_QTY)
                .newSchdDlvryQty(DEFAULT_NEW_SCHD_DLVRY_QTY)
                .upater(DEFAULT_UPATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return schedNom;
    }

    @Before
    public void initTest() {
        schedNom = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedNom() throws Exception {
        int databaseSizeBeforeCreate = schedNomRepository.findAll().size();

        // Create the SchedNom

        restSchedNomMockMvc.perform(post("/api/sched-noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedNom)))
            .andExpect(status().isCreated());

        // Validate the SchedNom in the database
        List<SchedNom> schedNomList = schedNomRepository.findAll();
        assertThat(schedNomList).hasSize(databaseSizeBeforeCreate + 1);
        SchedNom testSchedNom = schedNomList.get(schedNomList.size() - 1);
        assertThat(testSchedNom.getContrId()).isEqualTo(DEFAULT_CONTR_ID);
        assertThat(testSchedNom.getActivityNbr()).isEqualTo(DEFAULT_ACTIVITY_NBR);
        assertThat(testSchedNom.getGasDate()).isEqualTo(DEFAULT_GAS_DATE);
        assertThat(testSchedNom.getOldSchdRcptQty()).isEqualTo(DEFAULT_OLD_SCHD_RCPT_QTY);
        assertThat(testSchedNom.getNewSchdRcptQty()).isEqualTo(DEFAULT_NEW_SCHD_RCPT_QTY);
        assertThat(testSchedNom.getOldSchdDlvryQty()).isEqualTo(DEFAULT_OLD_SCHD_DLVRY_QTY);
        assertThat(testSchedNom.getNewSchdDlvryQty()).isEqualTo(DEFAULT_NEW_SCHD_DLVRY_QTY);
        assertThat(testSchedNom.getUpater()).isEqualTo(DEFAULT_UPATER);
        assertThat(testSchedNom.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createSchedNomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schedNomRepository.findAll().size();

        // Create the SchedNom with an existing ID
        SchedNom existingSchedNom = new SchedNom();
        existingSchedNom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchedNomMockMvc.perform(post("/api/sched-noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSchedNom)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SchedNom> schedNomList = schedNomRepository.findAll();
        assertThat(schedNomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchedNoms() throws Exception {
        // Initialize the database
        schedNomRepository.saveAndFlush(schedNom);

        // Get all the schedNomList
        restSchedNomMockMvc.perform(get("/api/sched-noms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedNom.getId().intValue())))
            .andExpect(jsonPath("$.[*].contrId").value(hasItem(DEFAULT_CONTR_ID.toString())))
            .andExpect(jsonPath("$.[*].activityNbr").value(hasItem(DEFAULT_ACTIVITY_NBR.toString())))
            .andExpect(jsonPath("$.[*].gasDate").value(hasItem(DEFAULT_GAS_DATE.toString())))
            .andExpect(jsonPath("$.[*].oldSchdRcptQty").value(hasItem(DEFAULT_OLD_SCHD_RCPT_QTY)))
            .andExpect(jsonPath("$.[*].newSchdRcptQty").value(hasItem(DEFAULT_NEW_SCHD_RCPT_QTY)))
            .andExpect(jsonPath("$.[*].oldSchdDlvryQty").value(hasItem(DEFAULT_OLD_SCHD_DLVRY_QTY)))
            .andExpect(jsonPath("$.[*].newSchdDlvryQty").value(hasItem(DEFAULT_NEW_SCHD_DLVRY_QTY)))
            .andExpect(jsonPath("$.[*].upater").value(hasItem(DEFAULT_UPATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getSchedNom() throws Exception {
        // Initialize the database
        schedNomRepository.saveAndFlush(schedNom);

        // Get the schedNom
        restSchedNomMockMvc.perform(get("/api/sched-noms/{id}", schedNom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schedNom.getId().intValue()))
            .andExpect(jsonPath("$.contrId").value(DEFAULT_CONTR_ID.toString()))
            .andExpect(jsonPath("$.activityNbr").value(DEFAULT_ACTIVITY_NBR.toString()))
            .andExpect(jsonPath("$.gasDate").value(DEFAULT_GAS_DATE.toString()))
            .andExpect(jsonPath("$.oldSchdRcptQty").value(DEFAULT_OLD_SCHD_RCPT_QTY))
            .andExpect(jsonPath("$.newSchdRcptQty").value(DEFAULT_NEW_SCHD_RCPT_QTY))
            .andExpect(jsonPath("$.oldSchdDlvryQty").value(DEFAULT_OLD_SCHD_DLVRY_QTY))
            .andExpect(jsonPath("$.newSchdDlvryQty").value(DEFAULT_NEW_SCHD_DLVRY_QTY))
            .andExpect(jsonPath("$.upater").value(DEFAULT_UPATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingSchedNom() throws Exception {
        // Get the schedNom
        restSchedNomMockMvc.perform(get("/api/sched-noms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedNom() throws Exception {
        // Initialize the database
        schedNomRepository.saveAndFlush(schedNom);
        int databaseSizeBeforeUpdate = schedNomRepository.findAll().size();

        // Update the schedNom
        SchedNom updatedSchedNom = schedNomRepository.findOne(schedNom.getId());
        updatedSchedNom
                .contrId(UPDATED_CONTR_ID)
                .activityNbr(UPDATED_ACTIVITY_NBR)
                .gasDate(UPDATED_GAS_DATE)
                .oldSchdRcptQty(UPDATED_OLD_SCHD_RCPT_QTY)
                .newSchdRcptQty(UPDATED_NEW_SCHD_RCPT_QTY)
                .oldSchdDlvryQty(UPDATED_OLD_SCHD_DLVRY_QTY)
                .newSchdDlvryQty(UPDATED_NEW_SCHD_DLVRY_QTY)
                .upater(UPDATED_UPATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restSchedNomMockMvc.perform(put("/api/sched-noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchedNom)))
            .andExpect(status().isOk());

        // Validate the SchedNom in the database
        List<SchedNom> schedNomList = schedNomRepository.findAll();
        assertThat(schedNomList).hasSize(databaseSizeBeforeUpdate);
        SchedNom testSchedNom = schedNomList.get(schedNomList.size() - 1);
        assertThat(testSchedNom.getContrId()).isEqualTo(UPDATED_CONTR_ID);
        assertThat(testSchedNom.getActivityNbr()).isEqualTo(UPDATED_ACTIVITY_NBR);
        assertThat(testSchedNom.getGasDate()).isEqualTo(UPDATED_GAS_DATE);
        assertThat(testSchedNom.getOldSchdRcptQty()).isEqualTo(UPDATED_OLD_SCHD_RCPT_QTY);
        assertThat(testSchedNom.getNewSchdRcptQty()).isEqualTo(UPDATED_NEW_SCHD_RCPT_QTY);
        assertThat(testSchedNom.getOldSchdDlvryQty()).isEqualTo(UPDATED_OLD_SCHD_DLVRY_QTY);
        assertThat(testSchedNom.getNewSchdDlvryQty()).isEqualTo(UPDATED_NEW_SCHD_DLVRY_QTY);
        assertThat(testSchedNom.getUpater()).isEqualTo(UPDATED_UPATER);
        assertThat(testSchedNom.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedNom() throws Exception {
        int databaseSizeBeforeUpdate = schedNomRepository.findAll().size();

        // Create the SchedNom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchedNomMockMvc.perform(put("/api/sched-noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schedNom)))
            .andExpect(status().isCreated());

        // Validate the SchedNom in the database
        List<SchedNom> schedNomList = schedNomRepository.findAll();
        assertThat(schedNomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchedNom() throws Exception {
        // Initialize the database
        schedNomRepository.saveAndFlush(schedNom);
        int databaseSizeBeforeDelete = schedNomRepository.findAll().size();

        // Get the schedNom
        restSchedNomMockMvc.perform(delete("/api/sched-noms/{id}", schedNom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchedNom> schedNomList = schedNomRepository.findAll();
        assertThat(schedNomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchedNom.class);
    }
}
