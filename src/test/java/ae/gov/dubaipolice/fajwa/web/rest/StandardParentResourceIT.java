package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.StandardParentAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import ae.gov.dubaipolice.fajwa.domain.StandardType;
import ae.gov.dubaipolice.fajwa.repository.StandardParentRepository;
import ae.gov.dubaipolice.fajwa.service.StandardParentService;
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
 * Integration tests for the {@link StandardParentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StandardParentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/standard-parents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandardParentRepository standardParentRepository;

    @Mock
    private StandardParentRepository standardParentRepositoryMock;

    @Mock
    private StandardParentService standardParentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandardParentMockMvc;

    private StandardParent standardParent;

    private StandardParent insertedStandardParent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardParent createEntity() {
        return new StandardParent().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardParent createUpdatedEntity() {
        return new StandardParent().name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        standardParent = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStandardParent != null) {
            standardParentRepository.delete(insertedStandardParent);
            insertedStandardParent = null;
        }
    }

    @Test
    @Transactional
    void createStandardParent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StandardParent
        var returnedStandardParent = om.readValue(
            restStandardParentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardParent)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StandardParent.class
        );

        // Validate the StandardParent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandardParentUpdatableFieldsEquals(returnedStandardParent, getPersistedStandardParent(returnedStandardParent));

        insertedStandardParent = returnedStandardParent;
    }

    @Test
    @Transactional
    void createStandardParentWithExistingId() throws Exception {
        // Create the StandardParent with an existing ID
        standardParent.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandardParentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardParent)))
            .andExpect(status().isBadRequest());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandardParents() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardParent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandardParentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(standardParentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandardParentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(standardParentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandardParentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(standardParentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandardParentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(standardParentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStandardParent() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get the standardParent
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL_ID, standardParent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standardParent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getStandardParentsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        Long id = standardParent.getId();

        defaultStandardParentFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStandardParentFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStandardParentFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStandardParentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList where name equals to
        defaultStandardParentFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardParentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList where name in
        defaultStandardParentFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardParentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList where name is not null
        defaultStandardParentFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllStandardParentsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList where name contains
        defaultStandardParentFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardParentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        // Get all the standardParentList where name does not contain
        defaultStandardParentFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllStandardParentsByStandardTypeIsEqualToSomething() throws Exception {
        StandardType standardType;
        if (TestUtil.findAll(em, StandardType.class).isEmpty()) {
            standardParentRepository.saveAndFlush(standardParent);
            standardType = StandardTypeResourceIT.createEntity();
        } else {
            standardType = TestUtil.findAll(em, StandardType.class).get(0);
        }
        em.persist(standardType);
        em.flush();
        standardParent.setStandardType(standardType);
        standardParentRepository.saveAndFlush(standardParent);
        Long standardTypeId = standardType.getId();
        // Get all the standardParentList where standardType equals to standardTypeId
        defaultStandardParentShouldBeFound("standardTypeId.equals=" + standardTypeId);

        // Get all the standardParentList where standardType equals to (standardTypeId + 1)
        defaultStandardParentShouldNotBeFound("standardTypeId.equals=" + (standardTypeId + 1));
    }

    private void defaultStandardParentFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStandardParentShouldBeFound(shouldBeFound);
        defaultStandardParentShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStandardParentShouldBeFound(String filter) throws Exception {
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardParent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStandardParentShouldNotBeFound(String filter) throws Exception {
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStandardParentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStandardParent() throws Exception {
        // Get the standardParent
        restStandardParentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandardParent() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardParent
        StandardParent updatedStandardParent = standardParentRepository.findById(standardParent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandardParent are not directly saved in db
        em.detach(updatedStandardParent);
        updatedStandardParent.name(UPDATED_NAME);

        restStandardParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandardParent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandardParent))
            )
            .andExpect(status().isOk());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandardParentToMatchAllProperties(updatedStandardParent);
    }

    @Test
    @Transactional
    void putNonExistingStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standardParent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardParent))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardParent))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardParent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandardParentWithPatch() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardParent using partial update
        StandardParent partialUpdatedStandardParent = new StandardParent();
        partialUpdatedStandardParent.setId(standardParent.getId());

        restStandardParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardParent))
            )
            .andExpect(status().isOk());

        // Validate the StandardParent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardParentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandardParent, standardParent),
            getPersistedStandardParent(standardParent)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandardParentWithPatch() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardParent using partial update
        StandardParent partialUpdatedStandardParent = new StandardParent();
        partialUpdatedStandardParent.setId(standardParent.getId());

        partialUpdatedStandardParent.name(UPDATED_NAME);

        restStandardParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardParent))
            )
            .andExpect(status().isOk());

        // Validate the StandardParent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardParentUpdatableFieldsEquals(partialUpdatedStandardParent, getPersistedStandardParent(partialUpdatedStandardParent));
    }

    @Test
    @Transactional
    void patchNonExistingStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standardParent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardParent))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardParent))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandardParent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardParent.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardParentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standardParent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardParent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandardParent() throws Exception {
        // Initialize the database
        insertedStandardParent = standardParentRepository.saveAndFlush(standardParent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standardParent
        restStandardParentMockMvc
            .perform(delete(ENTITY_API_URL_ID, standardParent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standardParentRepository.count();
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

    protected StandardParent getPersistedStandardParent(StandardParent standardParent) {
        return standardParentRepository.findById(standardParent.getId()).orElseThrow();
    }

    protected void assertPersistedStandardParentToMatchAllProperties(StandardParent expectedStandardParent) {
        assertStandardParentAllPropertiesEquals(expectedStandardParent, getPersistedStandardParent(expectedStandardParent));
    }

    protected void assertPersistedStandardParentToMatchUpdatableProperties(StandardParent expectedStandardParent) {
        assertStandardParentAllUpdatablePropertiesEquals(expectedStandardParent, getPersistedStandardParent(expectedStandardParent));
    }
}
