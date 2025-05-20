package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.JobDescAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.JobDesc;
import ae.gov.dubaipolice.fajwa.repository.JobDescRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link JobDescResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobDescResourceIT {

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-descs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JobDescRepository jobDescRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobDescMockMvc;

    private JobDesc jobDesc;

    private JobDesc insertedJobDesc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobDesc createEntity() {
        return new JobDesc().jobName(DEFAULT_JOB_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobDesc createUpdatedEntity() {
        return new JobDesc().jobName(UPDATED_JOB_NAME);
    }

    @BeforeEach
    void initTest() {
        jobDesc = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedJobDesc != null) {
            jobDescRepository.delete(insertedJobDesc);
            insertedJobDesc = null;
        }
    }

    @Test
    @Transactional
    void getAllJobDescs() throws Exception {
        // Initialize the database
        insertedJobDesc = jobDescRepository.saveAndFlush(jobDesc);

        // Get all the jobDescList
        restJobDescMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobDesc.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME)));
    }

    @Test
    @Transactional
    void getJobDesc() throws Exception {
        // Initialize the database
        insertedJobDesc = jobDescRepository.saveAndFlush(jobDesc);

        // Get the jobDesc
        restJobDescMockMvc
            .perform(get(ENTITY_API_URL_ID, jobDesc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobDesc.getId().intValue()))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME));
    }

    @Test
    @Transactional
    void getNonExistingJobDesc() throws Exception {
        // Get the jobDesc
        restJobDescMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return jobDescRepository.count();
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

    protected JobDesc getPersistedJobDesc(JobDesc jobDesc) {
        return jobDescRepository.findById(jobDesc.getId()).orElseThrow();
    }

    protected void assertPersistedJobDescToMatchAllProperties(JobDesc expectedJobDesc) {
        assertJobDescAllPropertiesEquals(expectedJobDesc, getPersistedJobDesc(expectedJobDesc));
    }

    protected void assertPersistedJobDescToMatchUpdatableProperties(JobDesc expectedJobDesc) {
        assertJobDescAllUpdatablePropertiesEquals(expectedJobDesc, getPersistedJobDesc(expectedJobDesc));
    }
}
