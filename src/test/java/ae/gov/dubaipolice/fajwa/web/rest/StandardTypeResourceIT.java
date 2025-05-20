package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.StandardTypeAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.StandardType;
import ae.gov.dubaipolice.fajwa.repository.StandardTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StandardTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StandardTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/standard-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandardTypeRepository standardTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandardTypeMockMvc;

    private StandardType standardType;

    private StandardType insertedStandardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardType createEntity() {
        return new StandardType().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardType createUpdatedEntity() {
        return new StandardType().name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        standardType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStandardType != null) {
            standardTypeRepository.delete(insertedStandardType);
            insertedStandardType = null;
        }
    }

    @Test
    @Transactional
    void createStandardType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StandardType
        var returnedStandardType = om.readValue(
            restStandardTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StandardType.class
        );

        // Validate the StandardType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandardTypeUpdatableFieldsEquals(returnedStandardType, getPersistedStandardType(returnedStandardType));

        insertedStandardType = returnedStandardType;
    }

    @Test
    @Transactional
    void createStandardTypeWithExistingId() throws Exception {
        // Create the StandardType with an existing ID
        standardType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandardTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardType)))
            .andExpect(status().isBadRequest());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandardTypes() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getStandardType() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get the standardType
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, standardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standardType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getStandardTypesByIdFiltering() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        Long id = standardType.getId();

        defaultStandardTypeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStandardTypeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStandardTypeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStandardTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList where name equals to
        defaultStandardTypeFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList where name in
        defaultStandardTypeFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList where name is not null
        defaultStandardTypeFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllStandardTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList where name contains
        defaultStandardTypeFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        // Get all the standardTypeList where name does not contain
        defaultStandardTypeFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    private void defaultStandardTypeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStandardTypeShouldBeFound(shouldBeFound);
        defaultStandardTypeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStandardTypeShouldBeFound(String filter) throws Exception {
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStandardTypeShouldNotBeFound(String filter) throws Exception {
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStandardTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStandardType() throws Exception {
        // Get the standardType
        restStandardTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandardType() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardType
        StandardType updatedStandardType = standardTypeRepository.findById(standardType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandardType are not directly saved in db
        em.detach(updatedStandardType);
        updatedStandardType.name(UPDATED_NAME);

        restStandardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandardType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandardType))
            )
            .andExpect(status().isOk());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandardTypeToMatchAllProperties(updatedStandardType);
    }

    @Test
    @Transactional
    void putNonExistingStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standardType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandardTypeWithPatch() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardType using partial update
        StandardType partialUpdatedStandardType = new StandardType();
        partialUpdatedStandardType.setId(standardType.getId());

        partialUpdatedStandardType.name(UPDATED_NAME);

        restStandardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardType))
            )
            .andExpect(status().isOk());

        // Validate the StandardType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandardType, standardType),
            getPersistedStandardType(standardType)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandardTypeWithPatch() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardType using partial update
        StandardType partialUpdatedStandardType = new StandardType();
        partialUpdatedStandardType.setId(standardType.getId());

        partialUpdatedStandardType.name(UPDATED_NAME);

        restStandardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardType))
            )
            .andExpect(status().isOk());

        // Validate the StandardType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardTypeUpdatableFieldsEquals(partialUpdatedStandardType, getPersistedStandardType(partialUpdatedStandardType));
    }

    @Test
    @Transactional
    void patchNonExistingStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardType))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandardType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standardType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandardType() throws Exception {
        // Initialize the database
        insertedStandardType = standardTypeRepository.saveAndFlush(standardType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standardType
        restStandardTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, standardType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standardTypeRepository.count();
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

    protected StandardType getPersistedStandardType(StandardType standardType) {
        return standardTypeRepository.findById(standardType.getId()).orElseThrow();
    }

    protected void assertPersistedStandardTypeToMatchAllProperties(StandardType expectedStandardType) {
        assertStandardTypeAllPropertiesEquals(expectedStandardType, getPersistedStandardType(expectedStandardType));
    }

    protected void assertPersistedStandardTypeToMatchUpdatableProperties(StandardType expectedStandardType) {
        assertStandardTypeAllUpdatablePropertiesEquals(expectedStandardType, getPersistedStandardType(expectedStandardType));
    }
}
