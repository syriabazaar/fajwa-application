package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.NominationAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.Employee;
import ae.gov.dubaipolice.fajwa.domain.JobVacant;
import ae.gov.dubaipolice.fajwa.domain.Nomination;
import ae.gov.dubaipolice.fajwa.repository.NominationRepository;
import ae.gov.dubaipolice.fajwa.service.NominationService;
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
 * Integration tests for the {@link NominationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NominationResourceIT {

    private static final Float DEFAULT_MACH_PERC = 1F;
    private static final Float UPDATED_MACH_PERC = 2F;
    private static final Float SMALLER_MACH_PERC = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/nominations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NominationRepository nominationRepository;

    @Mock
    private NominationRepository nominationRepositoryMock;

    @Mock
    private NominationService nominationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNominationMockMvc;

    private Nomination nomination;

    private Nomination insertedNomination;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nomination createEntity() {
        return new Nomination().machPerc(DEFAULT_MACH_PERC);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nomination createUpdatedEntity() {
        return new Nomination().machPerc(UPDATED_MACH_PERC);
    }

    @BeforeEach
    void initTest() {
        nomination = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedNomination != null) {
            nominationRepository.delete(insertedNomination);
            insertedNomination = null;
        }
    }

    @Test
    @Transactional
    void createNomination() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Nomination
        var returnedNomination = om.readValue(
            restNominationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nomination)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Nomination.class
        );

        // Validate the Nomination in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNominationUpdatableFieldsEquals(returnedNomination, getPersistedNomination(returnedNomination));

        insertedNomination = returnedNomination;
    }

    @Test
    @Transactional
    void createNominationWithExistingId() throws Exception {
        // Create the Nomination with an existing ID
        nomination.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNominationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nomination)))
            .andExpect(status().isBadRequest());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNominations() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList
        restNominationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nomination.getId().intValue())))
            .andExpect(jsonPath("$.[*].machPerc").value(hasItem(DEFAULT_MACH_PERC.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNominationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(nominationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNominationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(nominationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNominationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(nominationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNominationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(nominationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNomination() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get the nomination
        restNominationMockMvc
            .perform(get(ENTITY_API_URL_ID, nomination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nomination.getId().intValue()))
            .andExpect(jsonPath("$.machPerc").value(DEFAULT_MACH_PERC.doubleValue()));
    }

    @Test
    @Transactional
    void getNominationsByIdFiltering() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        Long id = nomination.getId();

        defaultNominationFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultNominationFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultNominationFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc equals to
        defaultNominationFiltering("machPerc.equals=" + DEFAULT_MACH_PERC, "machPerc.equals=" + UPDATED_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsInShouldWork() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc in
        defaultNominationFiltering("machPerc.in=" + DEFAULT_MACH_PERC + "," + UPDATED_MACH_PERC, "machPerc.in=" + UPDATED_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc is not null
        defaultNominationFiltering("machPerc.specified=true", "machPerc.specified=false");
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc is greater than or equal to
        defaultNominationFiltering("machPerc.greaterThanOrEqual=" + DEFAULT_MACH_PERC, "machPerc.greaterThanOrEqual=" + UPDATED_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc is less than or equal to
        defaultNominationFiltering("machPerc.lessThanOrEqual=" + DEFAULT_MACH_PERC, "machPerc.lessThanOrEqual=" + SMALLER_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc is less than
        defaultNominationFiltering("machPerc.lessThan=" + UPDATED_MACH_PERC, "machPerc.lessThan=" + DEFAULT_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByMachPercIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        // Get all the nominationList where machPerc is greater than
        defaultNominationFiltering("machPerc.greaterThan=" + SMALLER_MACH_PERC, "machPerc.greaterThan=" + DEFAULT_MACH_PERC);
    }

    @Test
    @Transactional
    void getAllNominationsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            nominationRepository.saveAndFlush(nomination);
            employee = EmployeeResourceIT.createEntity();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        nomination.setEmployee(employee);
        nominationRepository.saveAndFlush(nomination);
        Long employeeId = employee.getId();
        // Get all the nominationList where employee equals to employeeId
        defaultNominationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the nominationList where employee equals to (employeeId + 1)
        defaultNominationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllNominationsByJobVacantIsEqualToSomething() throws Exception {
        JobVacant jobVacant;
        if (TestUtil.findAll(em, JobVacant.class).isEmpty()) {
            nominationRepository.saveAndFlush(nomination);
            jobVacant = JobVacantResourceIT.createEntity();
        } else {
            jobVacant = TestUtil.findAll(em, JobVacant.class).get(0);
        }
        em.persist(jobVacant);
        em.flush();
        nomination.setJobVacant(jobVacant);
        nominationRepository.saveAndFlush(nomination);
        Long jobVacantId = jobVacant.getId();
        // Get all the nominationList where jobVacant equals to jobVacantId
        defaultNominationShouldBeFound("jobVacantId.equals=" + jobVacantId);

        // Get all the nominationList where jobVacant equals to (jobVacantId + 1)
        defaultNominationShouldNotBeFound("jobVacantId.equals=" + (jobVacantId + 1));
    }

    private void defaultNominationFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultNominationShouldBeFound(shouldBeFound);
        defaultNominationShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNominationShouldBeFound(String filter) throws Exception {
        restNominationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nomination.getId().intValue())))
            .andExpect(jsonPath("$.[*].machPerc").value(hasItem(DEFAULT_MACH_PERC.doubleValue())));

        // Check, that the count call also returns 1
        restNominationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNominationShouldNotBeFound(String filter) throws Exception {
        restNominationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNominationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNomination() throws Exception {
        // Get the nomination
        restNominationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNomination() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nomination
        Nomination updatedNomination = nominationRepository.findById(nomination.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNomination are not directly saved in db
        em.detach(updatedNomination);
        updatedNomination.machPerc(UPDATED_MACH_PERC);

        restNominationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNomination.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNomination))
            )
            .andExpect(status().isOk());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNominationToMatchAllProperties(updatedNomination);
    }

    @Test
    @Transactional
    void putNonExistingNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nomination.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nomination))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nomination))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nomination)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNominationWithPatch() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nomination using partial update
        Nomination partialUpdatedNomination = new Nomination();
        partialUpdatedNomination.setId(nomination.getId());

        partialUpdatedNomination.machPerc(UPDATED_MACH_PERC);

        restNominationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNomination.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNomination))
            )
            .andExpect(status().isOk());

        // Validate the Nomination in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNominationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNomination, nomination),
            getPersistedNomination(nomination)
        );
    }

    @Test
    @Transactional
    void fullUpdateNominationWithPatch() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nomination using partial update
        Nomination partialUpdatedNomination = new Nomination();
        partialUpdatedNomination.setId(nomination.getId());

        partialUpdatedNomination.machPerc(UPDATED_MACH_PERC);

        restNominationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNomination.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNomination))
            )
            .andExpect(status().isOk());

        // Validate the Nomination in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNominationUpdatableFieldsEquals(partialUpdatedNomination, getPersistedNomination(partialUpdatedNomination));
    }

    @Test
    @Transactional
    void patchNonExistingNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nomination.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nomination))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nomination))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNomination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nomination.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNominationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nomination)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nomination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNomination() throws Exception {
        // Initialize the database
        insertedNomination = nominationRepository.saveAndFlush(nomination);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nomination
        restNominationMockMvc
            .perform(delete(ENTITY_API_URL_ID, nomination.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nominationRepository.count();
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

    protected Nomination getPersistedNomination(Nomination nomination) {
        return nominationRepository.findById(nomination.getId()).orElseThrow();
    }

    protected void assertPersistedNominationToMatchAllProperties(Nomination expectedNomination) {
        assertNominationAllPropertiesEquals(expectedNomination, getPersistedNomination(expectedNomination));
    }

    protected void assertPersistedNominationToMatchUpdatableProperties(Nomination expectedNomination) {
        assertNominationAllUpdatablePropertiesEquals(expectedNomination, getPersistedNomination(expectedNomination));
    }
}
