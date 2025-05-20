package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtlsAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtls;
import ae.gov.dubaipolice.fajwa.domain.enumeration.StandardOption;
import ae.gov.dubaipolice.fajwa.repository.IntrvuStrdDtlsRepository;
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
 * Integration tests for the {@link IntrvuStrdDtlsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IntrvuStrdDtlsResourceIT {

    private static final StandardOption DEFAULT_STANDARD_OPTION = StandardOption.Yes;
    private static final StandardOption UPDATED_STANDARD_OPTION = StandardOption.No;

    private static final String ENTITY_API_URL = "/api/intrvu-strd-dtls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntrvuStrdDtlsRepository intrvuStrdDtlsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntrvuStrdDtlsMockMvc;

    private IntrvuStrdDtls intrvuStrdDtls;

    private IntrvuStrdDtls insertedIntrvuStrdDtls;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrvuStrdDtls createEntity() {
        return new IntrvuStrdDtls().standardOption(DEFAULT_STANDARD_OPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrvuStrdDtls createUpdatedEntity() {
        return new IntrvuStrdDtls().standardOption(UPDATED_STANDARD_OPTION);
    }

    @BeforeEach
    void initTest() {
        intrvuStrdDtls = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIntrvuStrdDtls != null) {
            intrvuStrdDtlsRepository.delete(insertedIntrvuStrdDtls);
            insertedIntrvuStrdDtls = null;
        }
    }

    @Test
    @Transactional
    void createIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IntrvuStrdDtls
        var returnedIntrvuStrdDtls = om.readValue(
            restIntrvuStrdDtlsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuStrdDtls)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntrvuStrdDtls.class
        );

        // Validate the IntrvuStrdDtls in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIntrvuStrdDtlsUpdatableFieldsEquals(returnedIntrvuStrdDtls, getPersistedIntrvuStrdDtls(returnedIntrvuStrdDtls));

        insertedIntrvuStrdDtls = returnedIntrvuStrdDtls;
    }

    @Test
    @Transactional
    void createIntrvuStrdDtlsWithExistingId() throws Exception {
        // Create the IntrvuStrdDtls with an existing ID
        intrvuStrdDtls.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntrvuStrdDtlsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuStrdDtls)))
            .andExpect(status().isBadRequest());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntrvuStrdDtls() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        // Get all the intrvuStrdDtlsList
        restIntrvuStrdDtlsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intrvuStrdDtls.getId().intValue())))
            .andExpect(jsonPath("$.[*].standardOption").value(hasItem(DEFAULT_STANDARD_OPTION.toString())));
    }

    @Test
    @Transactional
    void getIntrvuStrdDtls() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        // Get the intrvuStrdDtls
        restIntrvuStrdDtlsMockMvc
            .perform(get(ENTITY_API_URL_ID, intrvuStrdDtls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intrvuStrdDtls.getId().intValue()))
            .andExpect(jsonPath("$.standardOption").value(DEFAULT_STANDARD_OPTION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingIntrvuStrdDtls() throws Exception {
        // Get the intrvuStrdDtls
        restIntrvuStrdDtlsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntrvuStrdDtls() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuStrdDtls
        IntrvuStrdDtls updatedIntrvuStrdDtls = intrvuStrdDtlsRepository.findById(intrvuStrdDtls.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIntrvuStrdDtls are not directly saved in db
        em.detach(updatedIntrvuStrdDtls);
        updatedIntrvuStrdDtls.standardOption(UPDATED_STANDARD_OPTION);

        restIntrvuStrdDtlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIntrvuStrdDtls.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIntrvuStrdDtls))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntrvuStrdDtlsToMatchAllProperties(updatedIntrvuStrdDtls);
    }

    @Test
    @Transactional
    void putNonExistingIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intrvuStrdDtls.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intrvuStrdDtls))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intrvuStrdDtls))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuStrdDtls)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntrvuStrdDtlsWithPatch() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuStrdDtls using partial update
        IntrvuStrdDtls partialUpdatedIntrvuStrdDtls = new IntrvuStrdDtls();
        partialUpdatedIntrvuStrdDtls.setId(intrvuStrdDtls.getId());

        restIntrvuStrdDtlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrvuStrdDtls.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntrvuStrdDtls))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuStrdDtls in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntrvuStrdDtlsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntrvuStrdDtls, intrvuStrdDtls),
            getPersistedIntrvuStrdDtls(intrvuStrdDtls)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntrvuStrdDtlsWithPatch() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuStrdDtls using partial update
        IntrvuStrdDtls partialUpdatedIntrvuStrdDtls = new IntrvuStrdDtls();
        partialUpdatedIntrvuStrdDtls.setId(intrvuStrdDtls.getId());

        partialUpdatedIntrvuStrdDtls.standardOption(UPDATED_STANDARD_OPTION);

        restIntrvuStrdDtlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrvuStrdDtls.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntrvuStrdDtls))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuStrdDtls in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntrvuStrdDtlsUpdatableFieldsEquals(partialUpdatedIntrvuStrdDtls, getPersistedIntrvuStrdDtls(partialUpdatedIntrvuStrdDtls));
    }

    @Test
    @Transactional
    void patchNonExistingIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intrvuStrdDtls.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intrvuStrdDtls))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intrvuStrdDtls))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntrvuStrdDtls() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuStrdDtls.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuStrdDtlsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(intrvuStrdDtls)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrvuStrdDtls in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntrvuStrdDtls() throws Exception {
        // Initialize the database
        insertedIntrvuStrdDtls = intrvuStrdDtlsRepository.saveAndFlush(intrvuStrdDtls);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the intrvuStrdDtls
        restIntrvuStrdDtlsMockMvc
            .perform(delete(ENTITY_API_URL_ID, intrvuStrdDtls.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return intrvuStrdDtlsRepository.count();
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

    protected IntrvuStrdDtls getPersistedIntrvuStrdDtls(IntrvuStrdDtls intrvuStrdDtls) {
        return intrvuStrdDtlsRepository.findById(intrvuStrdDtls.getId()).orElseThrow();
    }

    protected void assertPersistedIntrvuStrdDtlsToMatchAllProperties(IntrvuStrdDtls expectedIntrvuStrdDtls) {
        assertIntrvuStrdDtlsAllPropertiesEquals(expectedIntrvuStrdDtls, getPersistedIntrvuStrdDtls(expectedIntrvuStrdDtls));
    }

    protected void assertPersistedIntrvuStrdDtlsToMatchUpdatableProperties(IntrvuStrdDtls expectedIntrvuStrdDtls) {
        assertIntrvuStrdDtlsAllUpdatablePropertiesEquals(expectedIntrvuStrdDtls, getPersistedIntrvuStrdDtls(expectedIntrvuStrdDtls));
    }
}
