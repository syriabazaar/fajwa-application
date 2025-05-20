package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.StandardReqAttachAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import ae.gov.dubaipolice.fajwa.repository.StandardReqAttachRepository;
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
 * Integration tests for the {@link StandardReqAttachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StandardReqAttachResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_ATT_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/standard-req-attaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandardReqAttachRepository standardReqAttachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandardReqAttachMockMvc;

    private StandardReqAttach standardReqAttach;

    private StandardReqAttach insertedStandardReqAttach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardReqAttach createEntity() {
        return new StandardReqAttach().name(DEFAULT_NAME).attDesc(DEFAULT_ATT_DESC);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardReqAttach createUpdatedEntity() {
        return new StandardReqAttach().name(UPDATED_NAME).attDesc(UPDATED_ATT_DESC);
    }

    @BeforeEach
    void initTest() {
        standardReqAttach = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStandardReqAttach != null) {
            standardReqAttachRepository.delete(insertedStandardReqAttach);
            insertedStandardReqAttach = null;
        }
    }

    @Test
    @Transactional
    void createStandardReqAttach() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StandardReqAttach
        var returnedStandardReqAttach = om.readValue(
            restStandardReqAttachMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardReqAttach)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StandardReqAttach.class
        );

        // Validate the StandardReqAttach in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandardReqAttachUpdatableFieldsEquals(returnedStandardReqAttach, getPersistedStandardReqAttach(returnedStandardReqAttach));

        insertedStandardReqAttach = returnedStandardReqAttach;
    }

    @Test
    @Transactional
    void createStandardReqAttachWithExistingId() throws Exception {
        // Create the StandardReqAttach with an existing ID
        standardReqAttach.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandardReqAttachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardReqAttach)))
            .andExpect(status().isBadRequest());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandardReqAttaches() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardReqAttach.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].attDesc").value(hasItem(DEFAULT_ATT_DESC)));
    }

    @Test
    @Transactional
    void getStandardReqAttach() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get the standardReqAttach
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL_ID, standardReqAttach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standardReqAttach.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.attDesc").value(DEFAULT_ATT_DESC));
    }

    @Test
    @Transactional
    void getStandardReqAttachesByIdFiltering() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        Long id = standardReqAttach.getId();

        defaultStandardReqAttachFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStandardReqAttachFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStandardReqAttachFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where name equals to
        defaultStandardReqAttachFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where name in
        defaultStandardReqAttachFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where name is not null
        defaultStandardReqAttachFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where name contains
        defaultStandardReqAttachFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where name does not contain
        defaultStandardReqAttachFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByAttDescIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where attDesc equals to
        defaultStandardReqAttachFiltering("attDesc.equals=" + DEFAULT_ATT_DESC, "attDesc.equals=" + UPDATED_ATT_DESC);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByAttDescIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where attDesc in
        defaultStandardReqAttachFiltering("attDesc.in=" + DEFAULT_ATT_DESC + "," + UPDATED_ATT_DESC, "attDesc.in=" + UPDATED_ATT_DESC);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByAttDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where attDesc is not null
        defaultStandardReqAttachFiltering("attDesc.specified=true", "attDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByAttDescContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where attDesc contains
        defaultStandardReqAttachFiltering("attDesc.contains=" + DEFAULT_ATT_DESC, "attDesc.contains=" + UPDATED_ATT_DESC);
    }

    @Test
    @Transactional
    void getAllStandardReqAttachesByAttDescNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        // Get all the standardReqAttachList where attDesc does not contain
        defaultStandardReqAttachFiltering("attDesc.doesNotContain=" + UPDATED_ATT_DESC, "attDesc.doesNotContain=" + DEFAULT_ATT_DESC);
    }

    private void defaultStandardReqAttachFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStandardReqAttachShouldBeFound(shouldBeFound);
        defaultStandardReqAttachShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStandardReqAttachShouldBeFound(String filter) throws Exception {
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardReqAttach.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].attDesc").value(hasItem(DEFAULT_ATT_DESC)));

        // Check, that the count call also returns 1
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStandardReqAttachShouldNotBeFound(String filter) throws Exception {
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStandardReqAttachMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStandardReqAttach() throws Exception {
        // Get the standardReqAttach
        restStandardReqAttachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandardReqAttach() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardReqAttach
        StandardReqAttach updatedStandardReqAttach = standardReqAttachRepository.findById(standardReqAttach.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandardReqAttach are not directly saved in db
        em.detach(updatedStandardReqAttach);
        updatedStandardReqAttach.name(UPDATED_NAME).attDesc(UPDATED_ATT_DESC);

        restStandardReqAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandardReqAttach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandardReqAttach))
            )
            .andExpect(status().isOk());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandardReqAttachToMatchAllProperties(updatedStandardReqAttach);
    }

    @Test
    @Transactional
    void putNonExistingStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standardReqAttach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardReqAttach))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardReqAttach))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardReqAttach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandardReqAttachWithPatch() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardReqAttach using partial update
        StandardReqAttach partialUpdatedStandardReqAttach = new StandardReqAttach();
        partialUpdatedStandardReqAttach.setId(standardReqAttach.getId());

        partialUpdatedStandardReqAttach.name(UPDATED_NAME).attDesc(UPDATED_ATT_DESC);

        restStandardReqAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardReqAttach.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardReqAttach))
            )
            .andExpect(status().isOk());

        // Validate the StandardReqAttach in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardReqAttachUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandardReqAttach, standardReqAttach),
            getPersistedStandardReqAttach(standardReqAttach)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandardReqAttachWithPatch() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardReqAttach using partial update
        StandardReqAttach partialUpdatedStandardReqAttach = new StandardReqAttach();
        partialUpdatedStandardReqAttach.setId(standardReqAttach.getId());

        partialUpdatedStandardReqAttach.name(UPDATED_NAME).attDesc(UPDATED_ATT_DESC);

        restStandardReqAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardReqAttach.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardReqAttach))
            )
            .andExpect(status().isOk());

        // Validate the StandardReqAttach in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardReqAttachUpdatableFieldsEquals(
            partialUpdatedStandardReqAttach,
            getPersistedStandardReqAttach(partialUpdatedStandardReqAttach)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standardReqAttach.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardReqAttach))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardReqAttach))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandardReqAttach() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardReqAttach.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardReqAttachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standardReqAttach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardReqAttach in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandardReqAttach() throws Exception {
        // Initialize the database
        insertedStandardReqAttach = standardReqAttachRepository.saveAndFlush(standardReqAttach);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standardReqAttach
        restStandardReqAttachMockMvc
            .perform(delete(ENTITY_API_URL_ID, standardReqAttach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standardReqAttachRepository.count();
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

    protected StandardReqAttach getPersistedStandardReqAttach(StandardReqAttach standardReqAttach) {
        return standardReqAttachRepository.findById(standardReqAttach.getId()).orElseThrow();
    }

    protected void assertPersistedStandardReqAttachToMatchAllProperties(StandardReqAttach expectedStandardReqAttach) {
        assertStandardReqAttachAllPropertiesEquals(expectedStandardReqAttach, getPersistedStandardReqAttach(expectedStandardReqAttach));
    }

    protected void assertPersistedStandardReqAttachToMatchUpdatableProperties(StandardReqAttach expectedStandardReqAttach) {
        assertStandardReqAttachAllUpdatablePropertiesEquals(
            expectedStandardReqAttach,
            getPersistedStandardReqAttach(expectedStandardReqAttach)
        );
    }
}
