package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.Nom;
import com.oilgascs.netra.repository.NomRepository;
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
 * Test class for the NomResource REST controller.
 *
 * @see NomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class NomResourceIntTest {

    private static final String DEFAULT_ACTIVITY_NBR = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_NBR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_GAS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_GAS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_REQ_RCPT_QTY = 1;
    private static final Integer UPDATED_REQ_RCPT_QTY = 2;

    private static final Integer DEFAULT_REQ_DLVRY_QTY = 1;
    private static final Integer UPDATED_REQ_DLVRY_QTY = 2;

    private static final Integer DEFAULT_OLD_SCHD_RCPT_QTY = 1;
    private static final Integer UPDATED_OLD_SCHD_RCPT_QTY = 2;

    private static final Integer DEFAULT_NEW_SCHD_RCPT_QTY = 1;
    private static final Integer UPDATED_NEW_SCHD_RCPT_QTY = 2;

    private static final Integer DEFAULT_OLD_SCHD_DLVRY_QTY = 1;
    private static final Integer UPDATED_OLD_SCHD_DLVRY_QTY = 2;

    private static final Integer DEFAULT_NEW_SCHD_DLVRY_QTY = 1;
    private static final Integer UPDATED_NEW_SCHD_DLVRY_QTY = 2;

    private static final String DEFAULT_UPDATER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private NomRepository nomRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNomMockMvc;

    private Nom nom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            NomResource nomResource = new NomResource(nomRepository);
        this.restNomMockMvc = MockMvcBuilders.standaloneSetup(nomResource)
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
    public static Nom createEntity(EntityManager em) {
        Nom nom = new Nom()
                .activityNbr(DEFAULT_ACTIVITY_NBR)
                .gasDate(DEFAULT_GAS_DATE)
                .reqRcptQty(DEFAULT_REQ_RCPT_QTY)
                .reqDlvryQty(DEFAULT_REQ_DLVRY_QTY)
                .oldSchdRcptQty(DEFAULT_OLD_SCHD_RCPT_QTY)
                .newSchdRcptQty(DEFAULT_NEW_SCHD_RCPT_QTY)
                .oldSchdDlvryQty(DEFAULT_OLD_SCHD_DLVRY_QTY)
                .newSchdDlvryQty(DEFAULT_NEW_SCHD_DLVRY_QTY)
                .updater(DEFAULT_UPDATER)
                .updateTimeStamp(DEFAULT_UPDATE_TIME_STAMP);
        return nom;
    }

    @Before
    public void initTest() {
        nom = createEntity(em);
    }

    @Test
    @Transactional
    public void createNom() throws Exception {
        int databaseSizeBeforeCreate = nomRepository.findAll().size();

        // Create the Nom

        restNomMockMvc.perform(post("/api/noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nom)))
            .andExpect(status().isCreated());

        // Validate the Nom in the database
        List<Nom> nomList = nomRepository.findAll();
        assertThat(nomList).hasSize(databaseSizeBeforeCreate + 1);
        Nom testNom = nomList.get(nomList.size() - 1);
        assertThat(testNom.getActivityNbr()).isEqualTo(DEFAULT_ACTIVITY_NBR);
        assertThat(testNom.getGasDate()).isEqualTo(DEFAULT_GAS_DATE);
        assertThat(testNom.getReqRcptQty()).isEqualTo(DEFAULT_REQ_RCPT_QTY);
        assertThat(testNom.getReqDlvryQty()).isEqualTo(DEFAULT_REQ_DLVRY_QTY);
        assertThat(testNom.getOldSchdRcptQty()).isEqualTo(DEFAULT_OLD_SCHD_RCPT_QTY);
        assertThat(testNom.getNewSchdRcptQty()).isEqualTo(DEFAULT_NEW_SCHD_RCPT_QTY);
        assertThat(testNom.getOldSchdDlvryQty()).isEqualTo(DEFAULT_OLD_SCHD_DLVRY_QTY);
        assertThat(testNom.getNewSchdDlvryQty()).isEqualTo(DEFAULT_NEW_SCHD_DLVRY_QTY);
        assertThat(testNom.getUpdater()).isEqualTo(DEFAULT_UPDATER);
        assertThat(testNom.getUpdateTimeStamp()).isEqualTo(DEFAULT_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createNomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nomRepository.findAll().size();

        // Create the Nom with an existing ID
        Nom existingNom = new Nom();
        existingNom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomMockMvc.perform(post("/api/noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingNom)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Nom> nomList = nomRepository.findAll();
        assertThat(nomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNoms() throws Exception {
        // Initialize the database
        nomRepository.saveAndFlush(nom);

        // Get all the nomList
        restNomMockMvc.perform(get("/api/noms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nom.getId().intValue())))
            .andExpect(jsonPath("$.[*].activityNbr").value(hasItem(DEFAULT_ACTIVITY_NBR.toString())))
            .andExpect(jsonPath("$.[*].gasDate").value(hasItem(DEFAULT_GAS_DATE.toString())))
            .andExpect(jsonPath("$.[*].reqRcptQty").value(hasItem(DEFAULT_REQ_RCPT_QTY)))
            .andExpect(jsonPath("$.[*].reqDlvryQty").value(hasItem(DEFAULT_REQ_DLVRY_QTY)))
            .andExpect(jsonPath("$.[*].oldSchdRcptQty").value(hasItem(DEFAULT_OLD_SCHD_RCPT_QTY)))
            .andExpect(jsonPath("$.[*].newSchdRcptQty").value(hasItem(DEFAULT_NEW_SCHD_RCPT_QTY)))
            .andExpect(jsonPath("$.[*].oldSchdDlvryQty").value(hasItem(DEFAULT_OLD_SCHD_DLVRY_QTY)))
            .andExpect(jsonPath("$.[*].newSchdDlvryQty").value(hasItem(DEFAULT_NEW_SCHD_DLVRY_QTY)))
            .andExpect(jsonPath("$.[*].updater").value(hasItem(DEFAULT_UPDATER.toString())))
            .andExpect(jsonPath("$.[*].updateTimeStamp").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getNom() throws Exception {
        // Initialize the database
        nomRepository.saveAndFlush(nom);

        // Get the nom
        restNomMockMvc.perform(get("/api/noms/{id}", nom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nom.getId().intValue()))
            .andExpect(jsonPath("$.activityNbr").value(DEFAULT_ACTIVITY_NBR.toString()))
            .andExpect(jsonPath("$.gasDate").value(DEFAULT_GAS_DATE.toString()))
            .andExpect(jsonPath("$.reqRcptQty").value(DEFAULT_REQ_RCPT_QTY))
            .andExpect(jsonPath("$.reqDlvryQty").value(DEFAULT_REQ_DLVRY_QTY))
            .andExpect(jsonPath("$.oldSchdRcptQty").value(DEFAULT_OLD_SCHD_RCPT_QTY))
            .andExpect(jsonPath("$.newSchdRcptQty").value(DEFAULT_NEW_SCHD_RCPT_QTY))
            .andExpect(jsonPath("$.oldSchdDlvryQty").value(DEFAULT_OLD_SCHD_DLVRY_QTY))
            .andExpect(jsonPath("$.newSchdDlvryQty").value(DEFAULT_NEW_SCHD_DLVRY_QTY))
            .andExpect(jsonPath("$.updater").value(DEFAULT_UPDATER.toString()))
            .andExpect(jsonPath("$.updateTimeStamp").value(sameInstant(DEFAULT_UPDATE_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingNom() throws Exception {
        // Get the nom
        restNomMockMvc.perform(get("/api/noms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNom() throws Exception {
        // Initialize the database
        nomRepository.saveAndFlush(nom);
        int databaseSizeBeforeUpdate = nomRepository.findAll().size();

        // Update the nom
        Nom updatedNom = nomRepository.findOne(nom.getId());
        updatedNom
                .activityNbr(UPDATED_ACTIVITY_NBR)
                .gasDate(UPDATED_GAS_DATE)
                .reqRcptQty(UPDATED_REQ_RCPT_QTY)
                .reqDlvryQty(UPDATED_REQ_DLVRY_QTY)
                .oldSchdRcptQty(UPDATED_OLD_SCHD_RCPT_QTY)
                .newSchdRcptQty(UPDATED_NEW_SCHD_RCPT_QTY)
                .oldSchdDlvryQty(UPDATED_OLD_SCHD_DLVRY_QTY)
                .newSchdDlvryQty(UPDATED_NEW_SCHD_DLVRY_QTY)
                .updater(UPDATED_UPDATER)
                .updateTimeStamp(UPDATED_UPDATE_TIME_STAMP);

        restNomMockMvc.perform(put("/api/noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNom)))
            .andExpect(status().isOk());

        // Validate the Nom in the database
        List<Nom> nomList = nomRepository.findAll();
        assertThat(nomList).hasSize(databaseSizeBeforeUpdate);
        Nom testNom = nomList.get(nomList.size() - 1);
        assertThat(testNom.getActivityNbr()).isEqualTo(UPDATED_ACTIVITY_NBR);
        assertThat(testNom.getGasDate()).isEqualTo(UPDATED_GAS_DATE);
        assertThat(testNom.getReqRcptQty()).isEqualTo(UPDATED_REQ_RCPT_QTY);
        assertThat(testNom.getReqDlvryQty()).isEqualTo(UPDATED_REQ_DLVRY_QTY);
        assertThat(testNom.getOldSchdRcptQty()).isEqualTo(UPDATED_OLD_SCHD_RCPT_QTY);
        assertThat(testNom.getNewSchdRcptQty()).isEqualTo(UPDATED_NEW_SCHD_RCPT_QTY);
        assertThat(testNom.getOldSchdDlvryQty()).isEqualTo(UPDATED_OLD_SCHD_DLVRY_QTY);
        assertThat(testNom.getNewSchdDlvryQty()).isEqualTo(UPDATED_NEW_SCHD_DLVRY_QTY);
        assertThat(testNom.getUpdater()).isEqualTo(UPDATED_UPDATER);
        assertThat(testNom.getUpdateTimeStamp()).isEqualTo(UPDATED_UPDATE_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingNom() throws Exception {
        int databaseSizeBeforeUpdate = nomRepository.findAll().size();

        // Create the Nom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNomMockMvc.perform(put("/api/noms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nom)))
            .andExpect(status().isCreated());

        // Validate the Nom in the database
        List<Nom> nomList = nomRepository.findAll();
        assertThat(nomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNom() throws Exception {
        // Initialize the database
        nomRepository.saveAndFlush(nom);
        int databaseSizeBeforeDelete = nomRepository.findAll().size();

        // Get the nom
        restNomMockMvc.perform(delete("/api/noms/{id}", nom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Nom> nomList = nomRepository.findAll();
        assertThat(nomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nom.class);
    }
}
