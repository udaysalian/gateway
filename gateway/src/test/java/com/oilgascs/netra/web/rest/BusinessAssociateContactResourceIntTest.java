package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.BusinessAssociateContact;
import com.oilgascs.netra.repository.BusinessAssociateContactRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BusinessAssociateContactResource REST controller.
 *
 * @see BusinessAssociateContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class BusinessAssociateContactResourceIntTest {

    private static final Long DEFAULT_BUSINESS_ASSOCIATE_CONTACT_ID = 1L;
    private static final Long UPDATED_BUSINESS_ASSOCIATE_CONTACT_ID = 2L;

    private static final LocalDate DEFAULT_BEGIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BEGIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BusinessAssociateContactRepository businessAssociateContactRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessAssociateContactMockMvc;

    private BusinessAssociateContact businessAssociateContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            BusinessAssociateContactResource businessAssociateContactResource = new BusinessAssociateContactResource(businessAssociateContactRepository);
        this.restBusinessAssociateContactMockMvc = MockMvcBuilders.standaloneSetup(businessAssociateContactResource)
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
    public static BusinessAssociateContact createEntity(EntityManager em) {
        BusinessAssociateContact businessAssociateContact = new BusinessAssociateContact()
                .businessAssociateContactId(DEFAULT_BUSINESS_ASSOCIATE_CONTACT_ID)
                .beginDate(DEFAULT_BEGIN_DATE)
                .endDate(DEFAULT_END_DATE);
        return businessAssociateContact;
    }

    @Before
    public void initTest() {
        businessAssociateContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessAssociateContact() throws Exception {
        int databaseSizeBeforeCreate = businessAssociateContactRepository.findAll().size();

        // Create the BusinessAssociateContact

        restBusinessAssociateContactMockMvc.perform(post("/api/business-associate-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessAssociateContact)))
            .andExpect(status().isCreated());

        // Validate the BusinessAssociateContact in the database
        List<BusinessAssociateContact> businessAssociateContactList = businessAssociateContactRepository.findAll();
        assertThat(businessAssociateContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessAssociateContact testBusinessAssociateContact = businessAssociateContactList.get(businessAssociateContactList.size() - 1);
        assertThat(testBusinessAssociateContact.getBusinessAssociateContactId()).isEqualTo(DEFAULT_BUSINESS_ASSOCIATE_CONTACT_ID);
        assertThat(testBusinessAssociateContact.getBeginDate()).isEqualTo(DEFAULT_BEGIN_DATE);
        assertThat(testBusinessAssociateContact.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createBusinessAssociateContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessAssociateContactRepository.findAll().size();

        // Create the BusinessAssociateContact with an existing ID
        BusinessAssociateContact existingBusinessAssociateContact = new BusinessAssociateContact();
        existingBusinessAssociateContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessAssociateContactMockMvc.perform(post("/api/business-associate-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBusinessAssociateContact)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BusinessAssociateContact> businessAssociateContactList = businessAssociateContactRepository.findAll();
        assertThat(businessAssociateContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessAssociateContacts() throws Exception {
        // Initialize the database
        businessAssociateContactRepository.saveAndFlush(businessAssociateContact);

        // Get all the businessAssociateContactList
        restBusinessAssociateContactMockMvc.perform(get("/api/business-associate-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessAssociateContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessAssociateContactId").value(hasItem(DEFAULT_BUSINESS_ASSOCIATE_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].beginDate").value(hasItem(DEFAULT_BEGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBusinessAssociateContact() throws Exception {
        // Initialize the database
        businessAssociateContactRepository.saveAndFlush(businessAssociateContact);

        // Get the businessAssociateContact
        restBusinessAssociateContactMockMvc.perform(get("/api/business-associate-contacts/{id}", businessAssociateContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessAssociateContact.getId().intValue()))
            .andExpect(jsonPath("$.businessAssociateContactId").value(DEFAULT_BUSINESS_ASSOCIATE_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.beginDate").value(DEFAULT_BEGIN_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessAssociateContact() throws Exception {
        // Get the businessAssociateContact
        restBusinessAssociateContactMockMvc.perform(get("/api/business-associate-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessAssociateContact() throws Exception {
        // Initialize the database
        businessAssociateContactRepository.saveAndFlush(businessAssociateContact);
        int databaseSizeBeforeUpdate = businessAssociateContactRepository.findAll().size();

        // Update the businessAssociateContact
        BusinessAssociateContact updatedBusinessAssociateContact = businessAssociateContactRepository.findOne(businessAssociateContact.getId());
        updatedBusinessAssociateContact
                .businessAssociateContactId(UPDATED_BUSINESS_ASSOCIATE_CONTACT_ID)
                .beginDate(UPDATED_BEGIN_DATE)
                .endDate(UPDATED_END_DATE);

        restBusinessAssociateContactMockMvc.perform(put("/api/business-associate-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessAssociateContact)))
            .andExpect(status().isOk());

        // Validate the BusinessAssociateContact in the database
        List<BusinessAssociateContact> businessAssociateContactList = businessAssociateContactRepository.findAll();
        assertThat(businessAssociateContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessAssociateContact testBusinessAssociateContact = businessAssociateContactList.get(businessAssociateContactList.size() - 1);
        assertThat(testBusinessAssociateContact.getBusinessAssociateContactId()).isEqualTo(UPDATED_BUSINESS_ASSOCIATE_CONTACT_ID);
        assertThat(testBusinessAssociateContact.getBeginDate()).isEqualTo(UPDATED_BEGIN_DATE);
        assertThat(testBusinessAssociateContact.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessAssociateContact() throws Exception {
        int databaseSizeBeforeUpdate = businessAssociateContactRepository.findAll().size();

        // Create the BusinessAssociateContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessAssociateContactMockMvc.perform(put("/api/business-associate-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessAssociateContact)))
            .andExpect(status().isCreated());

        // Validate the BusinessAssociateContact in the database
        List<BusinessAssociateContact> businessAssociateContactList = businessAssociateContactRepository.findAll();
        assertThat(businessAssociateContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusinessAssociateContact() throws Exception {
        // Initialize the database
        businessAssociateContactRepository.saveAndFlush(businessAssociateContact);
        int databaseSizeBeforeDelete = businessAssociateContactRepository.findAll().size();

        // Get the businessAssociateContact
        restBusinessAssociateContactMockMvc.perform(delete("/api/business-associate-contacts/{id}", businessAssociateContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessAssociateContact> businessAssociateContactList = businessAssociateContactRepository.findAll();
        assertThat(businessAssociateContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessAssociateContact.class);
    }
}
