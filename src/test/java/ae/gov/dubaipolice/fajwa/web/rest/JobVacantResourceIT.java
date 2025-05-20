package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.JobVacantAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.JobVacant;
import ae.gov.dubaipolice.fajwa.repository.JobVacantRepository;
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
 * Integration tests for the {@link JobVacantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class JobVacantResourceIT {

    private static final String ENTITY_API_URL = "/api/job-vacants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JobVacantRepository jobVacantRepository;

    @Mock
    private JobVacantRepository jobVacantRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobVacantMockMvc;

    private JobVacant jobVacant;

    private JobVacant insertedJobVacant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobVacant createEntity() {
        return new JobVacant();
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobVacant createUpdatedEntity() {
        return new JobVacant();
    }

    @BeforeEach
    void initTest() {
        jobVacant = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedJobVacant != null) {
            jobVacantRepository.delete(insertedJobVacant);
            insertedJobVacant = null;
        }
    }

    @Test
    @Transactional
    void createJobVacant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JobVacant
        var returnedJobVacant = om.readValue(
            restJobVacantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobVacant)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JobVacant.class
        );

        // Validate the JobVacant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertJobVacantUpdatableFieldsEquals(returnedJobVacant, getPersistedJobVacant(returnedJobVacant));

        insertedJobVacant = returnedJobVacant;
    }

    @Test
    @Transactional
    void createJobVacantWithExistingId() throws Exception {
        // Create the JobVacant with an existing ID
        jobVacant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobVacantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobVacant)))
            .andExpect(status().isBadRequest());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobVacants() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        // Get all the jobVacantList
        restJobVacantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobVacant.getId().intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJobVacantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(jobVacantRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobVacantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(jobVacantRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJobVacantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jobVacantRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobVacantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(jobVacantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getJobVacant() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        // Get the jobVacant
        restJobVacantMockMvc
            .perform(get(ENTITY_API_URL_ID, jobVacant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobVacant.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingJobVacant() throws Exception {
        // Get the jobVacant
        restJobVacantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJobVacant() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobVacant
        JobVacant updatedJobVacant = jobVacantRepository.findById(jobVacant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJobVacant are not directly saved in db
        em.detach(updatedJobVacant);

        restJobVacantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJobVacant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedJobVacant))
            )
            .andExpect(status().isOk());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJobVacantToMatchAllProperties(updatedJobVacant);
    }

    @Test
    @Transactional
    void putNonExistingJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobVacant.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobVacant))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jobVacant))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jobVacant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobVacantWithPatch() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobVacant using partial update
        JobVacant partialUpdatedJobVacant = new JobVacant();
        partialUpdatedJobVacant.setId(jobVacant.getId());

        restJobVacantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobVacant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobVacant))
            )
            .andExpect(status().isOk());

        // Validate the JobVacant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobVacantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJobVacant, jobVacant),
            getPersistedJobVacant(jobVacant)
        );
    }

    @Test
    @Transactional
    void fullUpdateJobVacantWithPatch() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jobVacant using partial update
        JobVacant partialUpdatedJobVacant = new JobVacant();
        partialUpdatedJobVacant.setId(jobVacant.getId());

        restJobVacantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobVacant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJobVacant))
            )
            .andExpect(status().isOk());

        // Validate the JobVacant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJobVacantUpdatableFieldsEquals(partialUpdatedJobVacant, getPersistedJobVacant(partialUpdatedJobVacant));
    }

    @Test
    @Transactional
    void patchNonExistingJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobVacant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobVacant))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jobVacant))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobVacant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jobVacant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobVacantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jobVacant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobVacant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobVacant() throws Exception {
        // Initialize the database
        insertedJobVacant = jobVacantRepository.saveAndFlush(jobVacant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jobVacant
        restJobVacantMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobVacant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jobVacantRepository.count();
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

    protected JobVacant getPersistedJobVacant(JobVacant jobVacant) {
        return jobVacantRepository.findById(jobVacant.getId()).orElseThrow();
    }

    protected void assertPersistedJobVacantToMatchAllProperties(JobVacant expectedJobVacant) {
        assertJobVacantAllPropertiesEquals(expectedJobVacant, getPersistedJobVacant(expectedJobVacant));
    }

    protected void assertPersistedJobVacantToMatchUpdatableProperties(JobVacant expectedJobVacant) {
        assertJobVacantAllUpdatablePropertiesEquals(expectedJobVacant, getPersistedJobVacant(expectedJobVacant));
    }
}
