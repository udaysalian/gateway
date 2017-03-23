package com.oilgascs.netra.web.rest;

import com.oilgascs.netra.NetraApp;

import com.oilgascs.netra.domain.BusinessAssociateAddress;
import com.oilgascs.netra.repository.BusinessAssociateAddressRepository;
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
 * Test class for the BusinessAssociateAddressResource REST controller.
 *
 * @see BusinessAssociateAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetraApp.class)
public class BusinessAssociateAddressResourceIntTest {

    private static final Long DEFAULT_BUSINESS_ASSOCIATE_ADDRESS_ID = 1L;
    private static final Long UPDATED_BUSINESS_ASSOCIATE_ADDRESS_ID = 2L;

    private static final String DEFAULT_ADD_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADD_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_NBR = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADD_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    @Autowired
    private BusinessAssociateAddressRepository businessAssociateAddressRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBusinessAssociateAddressMockMvc;

    private BusinessAssociateAddress businessAssociateAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            BusinessAssociateAddressResource businessAssociateAddressResource = new BusinessAssociateAddressResource(businessAssociateAddressRepository);
        this.restBusinessAssociateAddressMockMvc = MockMvcBuilders.standaloneSetup(businessAssociateAddressResource)
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
    public static BusinessAssociateAddress createEntity(EntityManager em) {
        BusinessAssociateAddress businessAssociateAddress = new BusinessAssociateAddress()
                .businessAssociateAddressId(DEFAULT_BUSINESS_ASSOCIATE_ADDRESS_ID)
                .addLine1(DEFAULT_ADD_LINE_1)
                .addressNbr(DEFAULT_ADDRESS_NBR)
                .addLine2(DEFAULT_ADD_LINE_2)
                .city(DEFAULT_CITY)
                .state(DEFAULT_STATE)
                .zipCode(DEFAULT_ZIP_CODE);
        return businessAssociateAddress;
    }

    @Before
    public void initTest() {
        businessAssociateAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessAssociateAddress() throws Exception {
        int databaseSizeBeforeCreate = businessAssociateAddressRepository.findAll().size();

        // Create the BusinessAssociateAddress

        restBusinessAssociateAddressMockMvc.perform(post("/api/business-associate-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessAssociateAddress)))
            .andExpect(status().isCreated());

        // Validate the BusinessAssociateAddress in the database
        List<BusinessAssociateAddress> businessAssociateAddressList = businessAssociateAddressRepository.findAll();
        assertThat(businessAssociateAddressList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessAssociateAddress testBusinessAssociateAddress = businessAssociateAddressList.get(businessAssociateAddressList.size() - 1);
        assertThat(testBusinessAssociateAddress.getBusinessAssociateAddressId()).isEqualTo(DEFAULT_BUSINESS_ASSOCIATE_ADDRESS_ID);
        assertThat(testBusinessAssociateAddress.getAddLine1()).isEqualTo(DEFAULT_ADD_LINE_1);
        assertThat(testBusinessAssociateAddress.getAddressNbr()).isEqualTo(DEFAULT_ADDRESS_NBR);
        assertThat(testBusinessAssociateAddress.getAddLine2()).isEqualTo(DEFAULT_ADD_LINE_2);
        assertThat(testBusinessAssociateAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBusinessAssociateAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testBusinessAssociateAddress.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
    }

    @Test
    @Transactional
    public void createBusinessAssociateAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessAssociateAddressRepository.findAll().size();

        // Create the BusinessAssociateAddress with an existing ID
        BusinessAssociateAddress existingBusinessAssociateAddress = new BusinessAssociateAddress();
        existingBusinessAssociateAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessAssociateAddressMockMvc.perform(post("/api/business-associate-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBusinessAssociateAddress)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BusinessAssociateAddress> businessAssociateAddressList = businessAssociateAddressRepository.findAll();
        assertThat(businessAssociateAddressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessAssociateAddresses() throws Exception {
        // Initialize the database
        businessAssociateAddressRepository.saveAndFlush(businessAssociateAddress);

        // Get all the businessAssociateAddressList
        restBusinessAssociateAddressMockMvc.perform(get("/api/business-associate-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessAssociateAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].businessAssociateAddressId").value(hasItem(DEFAULT_BUSINESS_ASSOCIATE_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].addLine1").value(hasItem(DEFAULT_ADD_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressNbr").value(hasItem(DEFAULT_ADDRESS_NBR.toString())))
            .andExpect(jsonPath("$.[*].addLine2").value(hasItem(DEFAULT_ADD_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())));
    }

    @Test
    @Transactional
    public void getBusinessAssociateAddress() throws Exception {
        // Initialize the database
        businessAssociateAddressRepository.saveAndFlush(businessAssociateAddress);

        // Get the businessAssociateAddress
        restBusinessAssociateAddressMockMvc.perform(get("/api/business-associate-addresses/{id}", businessAssociateAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(businessAssociateAddress.getId().intValue()))
            .andExpect(jsonPath("$.businessAssociateAddressId").value(DEFAULT_BUSINESS_ASSOCIATE_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.addLine1").value(DEFAULT_ADD_LINE_1.toString()))
            .andExpect(jsonPath("$.addressNbr").value(DEFAULT_ADDRESS_NBR.toString()))
            .andExpect(jsonPath("$.addLine2").value(DEFAULT_ADD_LINE_2.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessAssociateAddress() throws Exception {
        // Get the businessAssociateAddress
        restBusinessAssociateAddressMockMvc.perform(get("/api/business-associate-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessAssociateAddress() throws Exception {
        // Initialize the database
        businessAssociateAddressRepository.saveAndFlush(businessAssociateAddress);
        int databaseSizeBeforeUpdate = businessAssociateAddressRepository.findAll().size();

        // Update the businessAssociateAddress
        BusinessAssociateAddress updatedBusinessAssociateAddress = businessAssociateAddressRepository.findOne(businessAssociateAddress.getId());
        updatedBusinessAssociateAddress
                .businessAssociateAddressId(UPDATED_BUSINESS_ASSOCIATE_ADDRESS_ID)
                .addLine1(UPDATED_ADD_LINE_1)
                .addressNbr(UPDATED_ADDRESS_NBR)
                .addLine2(UPDATED_ADD_LINE_2)
                .city(UPDATED_CITY)
                .state(UPDATED_STATE)
                .zipCode(UPDATED_ZIP_CODE);

        restBusinessAssociateAddressMockMvc.perform(put("/api/business-associate-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBusinessAssociateAddress)))
            .andExpect(status().isOk());

        // Validate the BusinessAssociateAddress in the database
        List<BusinessAssociateAddress> businessAssociateAddressList = businessAssociateAddressRepository.findAll();
        assertThat(businessAssociateAddressList).hasSize(databaseSizeBeforeUpdate);
        BusinessAssociateAddress testBusinessAssociateAddress = businessAssociateAddressList.get(businessAssociateAddressList.size() - 1);
        assertThat(testBusinessAssociateAddress.getBusinessAssociateAddressId()).isEqualTo(UPDATED_BUSINESS_ASSOCIATE_ADDRESS_ID);
        assertThat(testBusinessAssociateAddress.getAddLine1()).isEqualTo(UPDATED_ADD_LINE_1);
        assertThat(testBusinessAssociateAddress.getAddressNbr()).isEqualTo(UPDATED_ADDRESS_NBR);
        assertThat(testBusinessAssociateAddress.getAddLine2()).isEqualTo(UPDATED_ADD_LINE_2);
        assertThat(testBusinessAssociateAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBusinessAssociateAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testBusinessAssociateAddress.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessAssociateAddress() throws Exception {
        int databaseSizeBeforeUpdate = businessAssociateAddressRepository.findAll().size();

        // Create the BusinessAssociateAddress

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBusinessAssociateAddressMockMvc.perform(put("/api/business-associate-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(businessAssociateAddress)))
            .andExpect(status().isCreated());

        // Validate the BusinessAssociateAddress in the database
        List<BusinessAssociateAddress> businessAssociateAddressList = businessAssociateAddressRepository.findAll();
        assertThat(businessAssociateAddressList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBusinessAssociateAddress() throws Exception {
        // Initialize the database
        businessAssociateAddressRepository.saveAndFlush(businessAssociateAddress);
        int databaseSizeBeforeDelete = businessAssociateAddressRepository.findAll().size();

        // Get the businessAssociateAddress
        restBusinessAssociateAddressMockMvc.perform(delete("/api/business-associate-addresses/{id}", businessAssociateAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BusinessAssociateAddress> businessAssociateAddressList = businessAssociateAddressRepository.findAll();
        assertThat(businessAssociateAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessAssociateAddress.class);
    }
}
