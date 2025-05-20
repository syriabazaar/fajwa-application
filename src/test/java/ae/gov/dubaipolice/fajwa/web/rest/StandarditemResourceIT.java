package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.StandarditemAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import ae.gov.dubaipolice.fajwa.domain.Standarditem;
import ae.gov.dubaipolice.fajwa.repository.StandarditemRepository;
import ae.gov.dubaipolice.fajwa.service.StandarditemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StandarditemResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StandarditemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Float DEFAULT_WEIGHT_PERCENTAGE = 1F;
    private static final Float UPDATED_WEIGHT_PERCENTAGE = 2F;
    private static final Float SMALLER_WEIGHT_PERCENTAGE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/standarditems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandarditemRepository standarditemRepository;

    @Mock
    private StandarditemRepository standarditemRepositoryMock;

    @Mock
    private StandarditemService standarditemServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandarditemMockMvc;

    private Standarditem standarditem;

    private Standarditem insertedStandarditem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Standarditem createEntity() {
        return new Standarditem().name(DEFAULT_NAME).isActive(DEFAULT_IS_ACTIVE).weightPercentage(DEFAULT_WEIGHT_PERCENTAGE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Standarditem createUpdatedEntity() {
        return new Standarditem().name(UPDATED_NAME).isActive(UPDATED_IS_ACTIVE).weightPercentage(UPDATED_WEIGHT_PERCENTAGE);
    }

    @BeforeEach
    void initTest() {
        standarditem = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStandarditem != null) {
            standarditemRepository.delete(insertedStandarditem);
            insertedStandarditem = null;
        }
    }

    @Test
    @Transactional
    void createStandarditem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Standarditem
        var returnedStandarditem = om.readValue(
            restStandarditemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standarditem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Standarditem.class
        );

        // Validate the Standarditem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandarditemUpdatableFieldsEquals(returnedStandarditem, getPersistedStandarditem(returnedStandarditem));

        insertedStandarditem = returnedStandarditem;
    }

    @Test
    @Transactional
    void createStandarditemWithExistingId() throws Exception {
        // Create the Standarditem with an existing ID
        standarditem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandarditemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standarditem)))
            .andExpect(status().isBadRequest());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandarditems() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standarditem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].weightPercentage").value(hasItem(DEFAULT_WEIGHT_PERCENTAGE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandarditemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(standarditemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandarditemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(standarditemServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandarditemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(standarditemServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandarditemMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(standarditemRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStandarditem() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get the standarditem
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL_ID, standarditem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standarditem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.weightPercentage").value(DEFAULT_WEIGHT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    void getStandarditemsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        Long id = standarditem.getId();

        defaultStandarditemFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStandarditemFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStandarditemFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStandarditemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where name equals to
        defaultStandarditemFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandarditemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where name in
        defaultStandarditemFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandarditemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where name is not null
        defaultStandarditemFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllStandarditemsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where name contains
        defaultStandarditemFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandarditemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where name does not contain
        defaultStandarditemFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllStandarditemsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where isActive equals to
        defaultStandarditemFiltering("isActive.equals=" + DEFAULT_IS_ACTIVE, "isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllStandarditemsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where isActive in
        defaultStandarditemFiltering("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE, "isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllStandarditemsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where isActive is not null
        defaultStandarditemFiltering("isActive.specified=true", "isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage equals to
        defaultStandarditemFiltering(
            "weightPercentage.equals=" + DEFAULT_WEIGHT_PERCENTAGE,
            "weightPercentage.equals=" + UPDATED_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage in
        defaultStandarditemFiltering(
            "weightPercentage.in=" + DEFAULT_WEIGHT_PERCENTAGE + "," + UPDATED_WEIGHT_PERCENTAGE,
            "weightPercentage.in=" + UPDATED_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage is not null
        defaultStandarditemFiltering("weightPercentage.specified=true", "weightPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage is greater than or equal to
        defaultStandarditemFiltering(
            "weightPercentage.greaterThanOrEqual=" + DEFAULT_WEIGHT_PERCENTAGE,
            "weightPercentage.greaterThanOrEqual=" + UPDATED_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage is less than or equal to
        defaultStandarditemFiltering(
            "weightPercentage.lessThanOrEqual=" + DEFAULT_WEIGHT_PERCENTAGE,
            "weightPercentage.lessThanOrEqual=" + SMALLER_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage is less than
        defaultStandarditemFiltering(
            "weightPercentage.lessThan=" + UPDATED_WEIGHT_PERCENTAGE,
            "weightPercentage.lessThan=" + DEFAULT_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByWeightPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        // Get all the standarditemList where weightPercentage is greater than
        defaultStandarditemFiltering(
            "weightPercentage.greaterThan=" + SMALLER_WEIGHT_PERCENTAGE,
            "weightPercentage.greaterThan=" + DEFAULT_WEIGHT_PERCENTAGE
        );
    }

    @Test
    @Transactional
    void getAllStandarditemsByStandardCatIsEqualToSomething() throws Exception {
        StandardCat standardCat;
        if (TestUtil.findAll(em, StandardCat.class).isEmpty()) {
            standarditemRepository.saveAndFlush(standarditem);
            standardCat = StandardCatResourceIT.createEntity();
        } else {
            standardCat = TestUtil.findAll(em, StandardCat.class).get(0);
        }
        em.persist(standardCat);
        em.flush();
        standarditem.setStandardCat(standardCat);
        standarditemRepository.saveAndFlush(standarditem);
        Long standardCatId = standardCat.getId();
        // Get all the standarditemList where standardCat equals to standardCatId
        defaultStandarditemShouldBeFound("standardCatId.equals=" + standardCatId);

        // Get all the standarditemList where standardCat equals to (standardCatId + 1)
        defaultStandarditemShouldNotBeFound("standardCatId.equals=" + (standardCatId + 1));
    }

    private void defaultStandarditemFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStandarditemShouldBeFound(shouldBeFound);
        defaultStandarditemShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStandarditemShouldBeFound(String filter) throws Exception {
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standarditem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].weightPercentage").value(hasItem(DEFAULT_WEIGHT_PERCENTAGE.doubleValue())));

        // Check, that the count call also returns 1
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStandarditemShouldNotBeFound(String filter) throws Exception {
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStandarditemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStandarditem() throws Exception {
        // Get the standarditem
        restStandarditemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandarditem() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standarditem
        Standarditem updatedStandarditem = standarditemRepository.findById(standarditem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandarditem are not directly saved in db
        em.detach(updatedStandarditem);
        updatedStandarditem.name(UPDATED_NAME).isActive(UPDATED_IS_ACTIVE).weightPercentage(UPDATED_WEIGHT_PERCENTAGE);

        restStandarditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandarditem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandarditem))
            )
            .andExpect(status().isOk());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandarditemToMatchAllProperties(updatedStandarditem);
    }

    @Test
    @Transactional
    void putNonExistingStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standarditem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standarditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standarditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standarditem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandarditemWithPatch() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standarditem using partial update
        Standarditem partialUpdatedStandarditem = new Standarditem();
        partialUpdatedStandarditem.setId(standarditem.getId());

        partialUpdatedStandarditem.weightPercentage(UPDATED_WEIGHT_PERCENTAGE);

        restStandarditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandarditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandarditem))
            )
            .andExpect(status().isOk());

        // Validate the Standarditem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandarditemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandarditem, standarditem),
            getPersistedStandarditem(standarditem)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandarditemWithPatch() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standarditem using partial update
        Standarditem partialUpdatedStandarditem = new Standarditem();
        partialUpdatedStandarditem.setId(standarditem.getId());

        partialUpdatedStandarditem.name(UPDATED_NAME).isActive(UPDATED_IS_ACTIVE).weightPercentage(UPDATED_WEIGHT_PERCENTAGE);

        restStandarditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandarditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandarditem))
            )
            .andExpect(status().isOk());

        // Validate the Standarditem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandarditemUpdatableFieldsEquals(partialUpdatedStandarditem, getPersistedStandarditem(partialUpdatedStandarditem));
    }

    @Test
    @Transactional
    void patchNonExistingStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standarditem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standarditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standarditem))
            )
            .andExpect(status().isBadRequest());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandarditem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standarditem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandarditemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standarditem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Standarditem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandarditem() throws Exception {
        // Initialize the database
        insertedStandarditem = standarditemRepository.saveAndFlush(standarditem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standarditem
        restStandarditemMockMvc
            .perform(delete(ENTITY_API_URL_ID, standarditem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standarditemRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Standarditem getPersistedStandarditem(Standarditem standarditem) {
        return standarditemRepository.findById(standarditem.getId()).orElseThrow();
    }

    protected void assertPersistedStandarditemToMatchAllProperties(Standarditem expectedStandarditem) {
        assertStandarditemAllPropertiesEquals(expectedStandarditem, getPersistedStandarditem(expectedStandarditem));
    }

    protected void assertPersistedStandarditemToMatchUpdatableProperties(Standarditem expectedStandarditem) {
        assertStandarditemAllUpdatablePropertiesEquals(expectedStandarditem, getPersistedStandarditem(expectedStandarditem));
    }
}
