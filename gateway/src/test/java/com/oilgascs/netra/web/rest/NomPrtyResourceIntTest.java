package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.NomPrty;
import com.oilgascs.netra.repository.NomPrtyRepository;
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
 * Test class for the NomPrtyResource REST controller.
 *
 * @see NomPrtyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class NomPrtyResourceIntTest {

    private static final String DEFAULT_CONTR_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_NBR = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NBR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GAS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GAS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRTY_TP = "AAAAAAAAAA";
    private static final String UPDATED_PRTY_TP = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRTY_QTY = 1;
    private static final Integer UPDATED_PRTY_QTY = 2;

    private static final String DEFAULT_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DIR_OF_FLOW = "AAAAAAAAAA";
    private static final String UPDATED_DIR_OF_FLOW = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private NomPrtyRepository nomPrtyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNomPrtyMockMvc;

    private NomPrty nomPrty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            NomPrtyResource nomPrtyResource = new NomPrtyResource(nomPrtyRepository);
        this.restNomPrtyMockMvc = MockMvcBuilders.standaloneSetup(nomPrtyResource)
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
    public static NomPrty createEntity(EntityManager em) {
        NomPrty nomPrty = new NomPrty()
                .contrId(DEFAULT_CONTR_ID)
                .activityNbr(DEFAULT_ACTIVITY_NBR)
                .gasDate(DEFAULT_GAS_DATE)
                .prtyTp(DEFAULT_PRTY_TP)
                .prtyQty(DEFAULT_PRTY_QTY)
                .subType(DEFAULT_SUB_TYPE)
                .dirOfFlow(DEFAULT_DIR_OF_FLOW)
                .updater(DEFAULT_UPDATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return nomPrty;
    }

    @Before
    public void initTest() {
        nomPrty = createEntity(em);
    }

    @Test
    @Transactional
    public void createNomPrty() throws Exception {
        int databaseSizeBeforeCreate = nomPrtyRepository.findAll().size();

        // Create the NomPrty

        restNomPrtyMockMvc.perform(post("/api/nom-prties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomPrty)))
            .andExpect(status().isCreated());

        // Validate the NomPrty in the database
        List<NomPrty> nomPrtyList = nomPrtyRepository.findAll();
        assertThat(nomPrtyList).hasSize(databaseSizeBeforeCreate + 1);
        NomPrty testNomPrty = nomPrtyList.get(nomPrtyList.size() - 1);
        assertThat(testNomPrty.getContrId()).isEqualTo(DEFAULT_CONTR_ID);
        assertThat(testNomPrty.getActivityNbr()).isEqualTo(DEFAULT_ACTIVITY_NBR);
        assertThat(testNomPrty.getGasDate()).isEqualTo(DEFAULT_GAS_DATE);
        assertThat(testNomPrty.getPrtyTp()).isEqualTo(DEFAULT_PRTY_TP);
        assertThat(testNomPrty.getPrtyQty()).isEqualTo(DEFAULT_PRTY_QTY);
        assertThat(testNomPrty.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testNomPrty.getDirOfFlow()).isEqualTo(DEFAULT_DIR_OF_FLOW);
        assertThat(testNomPrty.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testNomPrty.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createNomPrtyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nomPrtyRepository.findAll().size();

        // Create the NomPrty with an existing ID
        NomPrty existingNomPrty = new NomPrty();
        existingNomPrty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomPrtyMockMvc.perform(post("/api/nom-prties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNomPrty)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NomPrty> nomPrtyList = nomPrtyRepository.findAll();
        assertThat(nomPrtyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNomPrties() throws Exception {
        // Initialize the database
        nomPrtyRepository.saveAndFlush(nomPrty);

        // Get all the nomPrtyList
        restNomPrtyMockMvc.perform(get("/api/nom-prties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nomPrty.getId().intValue())))
            .andExpect(jsonPath("$.[*].contrId").value(hasItem(DEFAULT_CONTR_ID.toString())))
            .andExpect(jsonPath("$.[*].activityNbr").value(hasItem(DEFAULT_ACTIVITY_NBR.toString())))
            .andExpect(jsonPath("$.[*].gasDate").value(hasItem(DEFAULT_GAS_DATE.toString())))
            .andExpect(jsonPath("$.[*].prtyTp").value(hasItem(DEFAULT_PRTY_TP.toString())))
            .andExpect(jsonPath("$.[*].prtyQty").value(hasItem(DEFAULT_PRTY_QTY)))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dirOfFlow").value(hasItem(DEFAULT_DIR_OF_FLOW.toString())))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getNomPrty() throws Exception {
        // Initialize the database
        nomPrtyRepository.saveAndFlush(nomPrty);

        // Get the nomPrty
        restNomPrtyMockMvc.perform(get("/api/nom-prties/{id}", nomPrty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nomPrty.getId().intValue()))
            .andExpect(jsonPath("$.contrId").value(DEFAULT_CONTR_ID.toString()))
            .andExpect(jsonPath("$.activityNbr").value(DEFAULT_ACTIVITY_NBR.toString()))
            .andExpect(jsonPath("$.gasDate").value(DEFAULT_GAS_DATE.toString()))
            .andExpect(jsonPath("$.prtyTp").value(DEFAULT_PRTY_TP.toString()))
            .andExpect(jsonPath("$.prtyQty").value(DEFAULT_PRTY_QTY))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.dirOfFlow").value(DEFAULT_DIR_OF_FLOW.toString()))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingNomPrty() throws Exception {
        // Get the nomPrty
        restNomPrtyMockMvc.perform(get("/api/nom-prties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNomPrty() throws Exception {
        // Initialize the database
        nomPrtyRepository.saveAndFlush(nomPrty);
        int databaseSizeBeforeUpdate = nomPrtyRepository.findAll().size();

        // Update the nomPrty
        NomPrty updatedNomPrty = nomPrtyRepository.findOne(nomPrty.getId());
        updatedNomPrty
                .contrId(UPDATED_CONTR_ID)
                .activityNbr(UPDATED_ACTIVITY_NBR)
                .gasDate(UPDATED_GAS_DATE)
                .prtyTp(UPDATED_PRTY_TP)
                .prtyQty(UPDATED_PRTY_QTY)
                .subType(UPDATED_SUB_TYPE)
                .dirOfFlow(UPDATED_DIR_OF_FLOW)
                .updater(UPDATED_UPDATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restNomPrtyMockMvc.perform(put("/api/nom-prties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNomPrty)))
            .andExpect(status().isOk());

        // Validate the NomPrty in the database
        List<NomPrty> nomPrtyList = nomPrtyRepository.findAll();
        assertThat(nomPrtyList).hasSize(databaseSizeBeforeUpdate);
        NomPrty testNomPrty = nomPrtyList.get(nomPrtyList.size() - 1);
        assertThat(testNomPrty.getContrId()).isEqualTo(UPDATED_CONTR_ID);
        assertThat(testNomPrty.getActivityNbr()).isEqualTo(UPDATED_ACTIVITY_NBR);
        assertThat(testNomPrty.getGasDate()).isEqualTo(UPDATED_GAS_DATE);
        assertThat(testNomPrty.getPrtyTp()).isEqualTo(UPDATED_PRTY_TP);
        assertThat(testNomPrty.getPrtyQty()).isEqualTo(UPDATED_PRTY_QTY);
        assertThat(testNomPrty.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testNomPrty.getDirOfFlow()).isEqualTo(UPDATED_DIR_OF_FLOW);
        assertThat(testNomPrty.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testNomPrty.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingNomPrty() throws Exception {
        int databaseSizeBeforeUpdate = nomPrtyRepository.findAll().size();

        // Create the NomPrty

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNomPrtyMockMvc.perform(put("/api/nom-prties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nomPrty)))
            .andExpect(status().isCreated());

        // Validate the NomPrty in the database
        List<NomPrty> nomPrtyList = nomPrtyRepository.findAll();
        assertThat(nomPrtyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNomPrty() throws Exception {
        // Initialize the database
        nomPrtyRepository.saveAndFlush(nomPrty);
        int databaseSizeBeforeDelete = nomPrtyRepository.findAll().size();

        // Get the nomPrty
        restNomPrtyMockMvc.perform(delete("/api/nom-prties/{id}", nomPrty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NomPrty> nomPrtyList = nomPrtyRepository.findAll();
        assertThat(nomPrtyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NomPrty.class);
    }
}
