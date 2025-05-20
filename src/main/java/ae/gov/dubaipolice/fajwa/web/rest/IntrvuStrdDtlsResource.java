package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtls;
import ae.gov.dubaipolice.fajwa.repository.IntrvuStrdDtlsRepository;
import ae.gov.dubaipolice.fajwa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.IntrvuStrdDtls}.
 */
@RestController
@RequestMapping("/api/intrvu-strd-dtls")
@Transactional
public class IntrvuStrdDtlsResource {

    private static final Logger LOG = LoggerFactory.getLogger(IntrvuStrdDtlsResource.class);

    private static final String ENTITY_NAME = "intrvuStrdDtls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntrvuStrdDtlsRepository intrvuStrdDtlsRepository;

    public IntrvuStrdDtlsResource(IntrvuStrdDtlsRepository intrvuStrdDtlsRepository) {
        this.intrvuStrdDtlsRepository = intrvuStrdDtlsRepository;
    }

    /**
     * {@code POST  /intrvu-strd-dtls} : Create a new intrvuStrdDtls.
     *
     * @param intrvuStrdDtls the intrvuStrdDtls to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intrvuStrdDtls, or with status {@code 400 (Bad Request)} if the intrvuStrdDtls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntrvuStrdDtls> createIntrvuStrdDtls(@RequestBody IntrvuStrdDtls intrvuStrdDtls) throws URISyntaxException {
        LOG.debug("REST request to save IntrvuStrdDtls : {}", intrvuStrdDtls);
        if (intrvuStrdDtls.getId() != null) {
            throw new BadRequestAlertException("A new intrvuStrdDtls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        intrvuStrdDtls = intrvuStrdDtlsRepository.save(intrvuStrdDtls);
        return ResponseEntity.created(new URI("/api/intrvu-strd-dtls/" + intrvuStrdDtls.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, intrvuStrdDtls.getId().toString()))
            .body(intrvuStrdDtls);
    }

    /**
     * {@code PUT  /intrvu-strd-dtls/:id} : Updates an existing intrvuStrdDtls.
     *
     * @param id the id of the intrvuStrdDtls to save.
     * @param intrvuStrdDtls the intrvuStrdDtls to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrvuStrdDtls,
     * or with status {@code 400 (Bad Request)} if the intrvuStrdDtls is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intrvuStrdDtls couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntrvuStrdDtls> updateIntrvuStrdDtls(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrvuStrdDtls intrvuStrdDtls
    ) throws URISyntaxException {
        LOG.debug("REST request to update IntrvuStrdDtls : {}, {}", id, intrvuStrdDtls);
        if (intrvuStrdDtls.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrvuStrdDtls.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrvuStrdDtlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        intrvuStrdDtls = intrvuStrdDtlsRepository.save(intrvuStrdDtls);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intrvuStrdDtls.getId().toString()))
            .body(intrvuStrdDtls);
    }

    /**
     * {@code PATCH  /intrvu-strd-dtls/:id} : Partial updates given fields of an existing intrvuStrdDtls, field will ignore if it is null
     *
     * @param id the id of the intrvuStrdDtls to save.
     * @param intrvuStrdDtls the intrvuStrdDtls to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrvuStrdDtls,
     * or with status {@code 400 (Bad Request)} if the intrvuStrdDtls is not valid,
     * or with status {@code 404 (Not Found)} if the intrvuStrdDtls is not found,
     * or with status {@code 500 (Internal Server Error)} if the intrvuStrdDtls couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntrvuStrdDtls> partialUpdateIntrvuStrdDtls(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrvuStrdDtls intrvuStrdDtls
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IntrvuStrdDtls partially : {}, {}", id, intrvuStrdDtls);
        if (intrvuStrdDtls.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrvuStrdDtls.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrvuStrdDtlsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntrvuStrdDtls> result = intrvuStrdDtlsRepository
            .findById(intrvuStrdDtls.getId())
            .map(existingIntrvuStrdDtls -> {
                if (intrvuStrdDtls.getStandardOption() != null) {
                    existingIntrvuStrdDtls.setStandardOption(intrvuStrdDtls.getStandardOption());
                }

                return existingIntrvuStrdDtls;
            })
            .map(intrvuStrdDtlsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intrvuStrdDtls.getId().toString())
        );
    }

    /**
     * {@code GET  /intrvu-strd-dtls} : get all the intrvuStrdDtls.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intrvuStrdDtls in body.
     */
    @GetMapping("")
    public List<IntrvuStrdDtls> getAllIntrvuStrdDtls() {
        LOG.debug("REST request to get all IntrvuStrdDtls");
        return intrvuStrdDtlsRepository.findAll();
    }

    /**
     * {@code GET  /intrvu-strd-dtls/:id} : get the "id" intrvuStrdDtls.
     *
     * @param id the id of the intrvuStrdDtls to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intrvuStrdDtls, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntrvuStrdDtls> getIntrvuStrdDtls(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IntrvuStrdDtls : {}", id);
        Optional<IntrvuStrdDtls> intrvuStrdDtls = intrvuStrdDtlsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(intrvuStrdDtls);
    }

    /**
     * {@code DELETE  /intrvu-strd-dtls/:id} : delete the "id" intrvuStrdDtls.
     *
     * @param id the id of the intrvuStrdDtls to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntrvuStrdDtls(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IntrvuStrdDtls : {}", id);
        intrvuStrdDtlsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
