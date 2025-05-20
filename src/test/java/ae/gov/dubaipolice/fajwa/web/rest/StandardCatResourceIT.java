package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.StandardCatAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import ae.gov.dubaipolice.fajwa.repository.StandardCatRepository;
import ae.gov.dubaipolice.fajwa.service.StandardCatService;
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
 * Integration tests for the {@link StandardCatResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StandardCatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/standard-cats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StandardCatRepository standardCatRepository;

    @Mock
    private StandardCatRepository standardCatRepositoryMock;

    @Mock
    private StandardCatService standardCatServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStandardCatMockMvc;

    private StandardCat standardCat;

    private StandardCat insertedStandardCat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardCat createEntity() {
        return new StandardCat().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StandardCat createUpdatedEntity() {
        return new StandardCat().name(UPDATED_NAME);
    }

    @BeforeEach
    void initTest() {
        standardCat = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedStandardCat != null) {
            standardCatRepository.delete(insertedStandardCat);
            insertedStandardCat = null;
        }
    }

    @Test
    @Transactional
    void createStandardCat() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StandardCat
        var returnedStandardCat = om.readValue(
            restStandardCatMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardCat)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StandardCat.class
        );

        // Validate the StandardCat in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertStandardCatUpdatableFieldsEquals(returnedStandardCat, getPersistedStandardCat(returnedStandardCat));

        insertedStandardCat = returnedStandardCat;
    }

    @Test
    @Transactional
    void createStandardCatWithExistingId() throws Exception {
        // Create the StandardCat with an existing ID
        standardCat.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStandardCatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardCat)))
            .andExpect(status().isBadRequest());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStandardCats() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandardCatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(standardCatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandardCatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(standardCatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStandardCatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(standardCatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStandardCatMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(standardCatRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStandardCat() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get the standardCat
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL_ID, standardCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(standardCat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getStandardCatsByIdFiltering() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        Long id = standardCat.getId();

        defaultStandardCatFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultStandardCatFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultStandardCatFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStandardCatsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList where name equals to
        defaultStandardCatFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardCatsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList where name in
        defaultStandardCatFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardCatsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList where name is not null
        defaultStandardCatFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllStandardCatsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList where name contains
        defaultStandardCatFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStandardCatsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        // Get all the standardCatList where name does not contain
        defaultStandardCatFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllStandardCatsByStandardParentIsEqualToSomething() throws Exception {
        StandardParent standardParent;
        if (TestUtil.findAll(em, StandardParent.class).isEmpty()) {
            standardCatRepository.saveAndFlush(standardCat);
            standardParent = StandardParentResourceIT.createEntity();
        } else {
            standardParent = TestUtil.findAll(em, StandardParent.class).get(0);
        }
        em.persist(standardParent);
        em.flush();
        standardCat.setStandardParent(standardParent);
        standardCatRepository.saveAndFlush(standardCat);
        Long standardParentId = standardParent.getId();
        // Get all the standardCatList where standardParent equals to standardParentId
        defaultStandardCatShouldBeFound("standardParentId.equals=" + standardParentId);

        // Get all the standardCatList where standardParent equals to (standardParentId + 1)
        defaultStandardCatShouldNotBeFound("standardParentId.equals=" + (standardParentId + 1));
    }

    @Test
    @Transactional
    void getAllStandardCatsByReqAttachmentIsEqualToSomething() throws Exception {
        StandardReqAttach reqAttachment;
        if (TestUtil.findAll(em, StandardReqAttach.class).isEmpty()) {
            standardCatRepository.saveAndFlush(standardCat);
            reqAttachment = StandardReqAttachResourceIT.createEntity();
        } else {
            reqAttachment = TestUtil.findAll(em, StandardReqAttach.class).get(0);
        }
        em.persist(reqAttachment);
        em.flush();
        standardCat.setReqAttachment(reqAttachment);
        standardCatRepository.saveAndFlush(standardCat);
        Long reqAttachmentId = reqAttachment.getId();
        // Get all the standardCatList where reqAttachment equals to reqAttachmentId
        defaultStandardCatShouldBeFound("reqAttachmentId.equals=" + reqAttachmentId);

        // Get all the standardCatList where reqAttachment equals to (reqAttachmentId + 1)
        defaultStandardCatShouldNotBeFound("reqAttachmentId.equals=" + (reqAttachmentId + 1));
    }

    private void defaultStandardCatFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultStandardCatShouldBeFound(shouldBeFound);
        defaultStandardCatShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStandardCatShouldBeFound(String filter) throws Exception {
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(standardCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStandardCatShouldNotBeFound(String filter) throws Exception {
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStandardCatMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStandardCat() throws Exception {
        // Get the standardCat
        restStandardCatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStandardCat() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardCat
        StandardCat updatedStandardCat = standardCatRepository.findById(standardCat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStandardCat are not directly saved in db
        em.detach(updatedStandardCat);
        updatedStandardCat.name(UPDATED_NAME);

        restStandardCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStandardCat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedStandardCat))
            )
            .andExpect(status().isOk());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStandardCatToMatchAllProperties(updatedStandardCat);
    }

    @Test
    @Transactional
    void putNonExistingStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, standardCat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardCat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(standardCat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(standardCat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStandardCatWithPatch() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardCat using partial update
        StandardCat partialUpdatedStandardCat = new StandardCat();
        partialUpdatedStandardCat.setId(standardCat.getId());

        restStandardCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardCat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardCat))
            )
            .andExpect(status().isOk());

        // Validate the StandardCat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardCatUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStandardCat, standardCat),
            getPersistedStandardCat(standardCat)
        );
    }

    @Test
    @Transactional
    void fullUpdateStandardCatWithPatch() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the standardCat using partial update
        StandardCat partialUpdatedStandardCat = new StandardCat();
        partialUpdatedStandardCat.setId(standardCat.getId());

        partialUpdatedStandardCat.name(UPDATED_NAME);

        restStandardCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStandardCat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStandardCat))
            )
            .andExpect(status().isOk());

        // Validate the StandardCat in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStandardCatUpdatableFieldsEquals(partialUpdatedStandardCat, getPersistedStandardCat(partialUpdatedStandardCat));
    }

    @Test
    @Transactional
    void patchNonExistingStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, standardCat.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardCat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(standardCat))
            )
            .andExpect(status().isBadRequest());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStandardCat() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        standardCat.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStandardCatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(standardCat)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StandardCat in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStandardCat() throws Exception {
        // Initialize the database
        insertedStandardCat = standardCatRepository.saveAndFlush(standardCat);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the standardCat
        restStandardCatMockMvc
            .perform(delete(ENTITY_API_URL_ID, standardCat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return standardCatRepository.count();
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

    protected StandardCat getPersistedStandardCat(StandardCat standardCat) {
        return standardCatRepository.findById(standardCat.getId()).orElseThrow();
    }

    protected void assertPersistedStandardCatToMatchAllProperties(StandardCat expectedStandardCat) {
        assertStandardCatAllPropertiesEquals(expectedStandardCat, getPersistedStandardCat(expectedStandardCat));
    }

    protected void assertPersistedStandardCatToMatchUpdatableProperties(StandardCat expectedStandardCat) {
        assertStandardCatAllUpdatablePropertiesEquals(expectedStandardCat, getPersistedStandardCat(expectedStandardCat));
    }
}
