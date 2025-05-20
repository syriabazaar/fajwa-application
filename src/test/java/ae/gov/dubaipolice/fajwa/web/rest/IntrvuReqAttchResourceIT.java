package ae.gov.dubaipolice.fajwa.web.rest;

import static ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttchAsserts.*;
import static ae.gov.dubaipolice.fajwa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ae.gov.dubaipolice.fajwa.IntegrationTest;
import ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttch;
import ae.gov.dubaipolice.fajwa.repository.IntrvuReqAttchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
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
 * Integration tests for the {@link IntrvuReqAttchResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IntrvuReqAttchResourceIT {

    private static final Instant DEFAULT_ADD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ADD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_ATTCH = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTCH = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTCH_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTCH_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/intrvu-req-attches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IntrvuReqAttchRepository intrvuReqAttchRepository;

    @Mock
    private IntrvuReqAttchRepository intrvuReqAttchRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntrvuReqAttchMockMvc;

    private IntrvuReqAttch intrvuReqAttch;

    private IntrvuReqAttch insertedIntrvuReqAttch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrvuReqAttch createEntity() {
        return new IntrvuReqAttch().addDate(DEFAULT_ADD_DATE).attch(DEFAULT_ATTCH).attchContentType(DEFAULT_ATTCH_CONTENT_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IntrvuReqAttch createUpdatedEntity() {
        return new IntrvuReqAttch().addDate(UPDATED_ADD_DATE).attch(UPDATED_ATTCH).attchContentType(UPDATED_ATTCH_CONTENT_TYPE);
    }

    @BeforeEach
    void initTest() {
        intrvuReqAttch = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedIntrvuReqAttch != null) {
            intrvuReqAttchRepository.delete(insertedIntrvuReqAttch);
            insertedIntrvuReqAttch = null;
        }
    }

    @Test
    @Transactional
    void createIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IntrvuReqAttch
        var returnedIntrvuReqAttch = om.readValue(
            restIntrvuReqAttchMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuReqAttch)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IntrvuReqAttch.class
        );

        // Validate the IntrvuReqAttch in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIntrvuReqAttchUpdatableFieldsEquals(returnedIntrvuReqAttch, getPersistedIntrvuReqAttch(returnedIntrvuReqAttch));

        insertedIntrvuReqAttch = returnedIntrvuReqAttch;
    }

    @Test
    @Transactional
    void createIntrvuReqAttchWithExistingId() throws Exception {
        // Create the IntrvuReqAttch with an existing ID
        intrvuReqAttch.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntrvuReqAttchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuReqAttch)))
            .andExpect(status().isBadRequest());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIntrvuReqAttches() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        // Get all the intrvuReqAttchList
        restIntrvuReqAttchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intrvuReqAttch.getId().intValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].attchContentType").value(hasItem(DEFAULT_ATTCH_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attch").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ATTCH))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIntrvuReqAttchesWithEagerRelationshipsIsEnabled() throws Exception {
        when(intrvuReqAttchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIntrvuReqAttchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(intrvuReqAttchRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllIntrvuReqAttchesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(intrvuReqAttchRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restIntrvuReqAttchMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(intrvuReqAttchRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getIntrvuReqAttch() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        // Get the intrvuReqAttch
        restIntrvuReqAttchMockMvc
            .perform(get(ENTITY_API_URL_ID, intrvuReqAttch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intrvuReqAttch.getId().intValue()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.attchContentType").value(DEFAULT_ATTCH_CONTENT_TYPE))
            .andExpect(jsonPath("$.attch").value(Base64.getEncoder().encodeToString(DEFAULT_ATTCH)));
    }

    @Test
    @Transactional
    void getNonExistingIntrvuReqAttch() throws Exception {
        // Get the intrvuReqAttch
        restIntrvuReqAttchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIntrvuReqAttch() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuReqAttch
        IntrvuReqAttch updatedIntrvuReqAttch = intrvuReqAttchRepository.findById(intrvuReqAttch.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIntrvuReqAttch are not directly saved in db
        em.detach(updatedIntrvuReqAttch);
        updatedIntrvuReqAttch.addDate(UPDATED_ADD_DATE).attch(UPDATED_ATTCH).attchContentType(UPDATED_ATTCH_CONTENT_TYPE);

        restIntrvuReqAttchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIntrvuReqAttch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIntrvuReqAttch))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIntrvuReqAttchToMatchAllProperties(updatedIntrvuReqAttch);
    }

    @Test
    @Transactional
    void putNonExistingIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, intrvuReqAttch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intrvuReqAttch))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(intrvuReqAttch))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(intrvuReqAttch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIntrvuReqAttchWithPatch() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuReqAttch using partial update
        IntrvuReqAttch partialUpdatedIntrvuReqAttch = new IntrvuReqAttch();
        partialUpdatedIntrvuReqAttch.setId(intrvuReqAttch.getId());

        partialUpdatedIntrvuReqAttch.addDate(UPDATED_ADD_DATE);

        restIntrvuReqAttchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrvuReqAttch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntrvuReqAttch))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuReqAttch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntrvuReqAttchUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIntrvuReqAttch, intrvuReqAttch),
            getPersistedIntrvuReqAttch(intrvuReqAttch)
        );
    }

    @Test
    @Transactional
    void fullUpdateIntrvuReqAttchWithPatch() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the intrvuReqAttch using partial update
        IntrvuReqAttch partialUpdatedIntrvuReqAttch = new IntrvuReqAttch();
        partialUpdatedIntrvuReqAttch.setId(intrvuReqAttch.getId());

        partialUpdatedIntrvuReqAttch.addDate(UPDATED_ADD_DATE).attch(UPDATED_ATTCH).attchContentType(UPDATED_ATTCH_CONTENT_TYPE);

        restIntrvuReqAttchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIntrvuReqAttch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIntrvuReqAttch))
            )
            .andExpect(status().isOk());

        // Validate the IntrvuReqAttch in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIntrvuReqAttchUpdatableFieldsEquals(partialUpdatedIntrvuReqAttch, getPersistedIntrvuReqAttch(partialUpdatedIntrvuReqAttch));
    }

    @Test
    @Transactional
    void patchNonExistingIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, intrvuReqAttch.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intrvuReqAttch))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(intrvuReqAttch))
            )
            .andExpect(status().isBadRequest());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIntrvuReqAttch() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        intrvuReqAttch.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIntrvuReqAttchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(intrvuReqAttch)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IntrvuReqAttch in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIntrvuReqAttch() throws Exception {
        // Initialize the database
        insertedIntrvuReqAttch = intrvuReqAttchRepository.saveAndFlush(intrvuReqAttch);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the intrvuReqAttch
        restIntrvuReqAttchMockMvc
            .perform(delete(ENTITY_API_URL_ID, intrvuReqAttch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return intrvuReqAttchRepository.count();
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

    protected IntrvuReqAttch getPersistedIntrvuReqAttch(IntrvuReqAttch intrvuReqAttch) {
        return intrvuReqAttchRepository.findById(intrvuReqAttch.getId()).orElseThrow();
    }

    protected void assertPersistedIntrvuReqAttchToMatchAllProperties(IntrvuReqAttch expectedIntrvuReqAttch) {
        assertIntrvuReqAttchAllPropertiesEquals(expectedIntrvuReqAttch, getPersistedIntrvuReqAttch(expectedIntrvuReqAttch));
    }

    protected void assertPersistedIntrvuReqAttchToMatchUpdatableProperties(IntrvuReqAttch expectedIntrvuReqAttch) {
        assertIntrvuReqAttchAllUpdatablePropertiesEquals(expectedIntrvuReqAttch, getPersistedIntrvuReqAttch(expectedIntrvuReqAttch));
    }
}
