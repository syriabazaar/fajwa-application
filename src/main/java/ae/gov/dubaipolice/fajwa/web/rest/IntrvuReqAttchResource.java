package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttch;
import ae.gov.dubaipolice.fajwa.repository.IntrvuReqAttchRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.IntrvuReqAttch}.
 */
@RestController
@RequestMapping("/api/intrvu-req-attches")
@Transactional
public class IntrvuReqAttchResource {

    private static final Logger LOG = LoggerFactory.getLogger(IntrvuReqAttchResource.class);

    private static final String ENTITY_NAME = "intrvuReqAttch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntrvuReqAttchRepository intrvuReqAttchRepository;

    public IntrvuReqAttchResource(IntrvuReqAttchRepository intrvuReqAttchRepository) {
        this.intrvuReqAttchRepository = intrvuReqAttchRepository;
    }

    /**
     * {@code POST  /intrvu-req-attches} : Create a new intrvuReqAttch.
     *
     * @param intrvuReqAttch the intrvuReqAttch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intrvuReqAttch, or with status {@code 400 (Bad Request)} if the intrvuReqAttch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IntrvuReqAttch> createIntrvuReqAttch(@RequestBody IntrvuReqAttch intrvuReqAttch) throws URISyntaxException {
        LOG.debug("REST request to save IntrvuReqAttch : {}", intrvuReqAttch);
        if (intrvuReqAttch.getId() != null) {
            throw new BadRequestAlertException("A new intrvuReqAttch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        intrvuReqAttch = intrvuReqAttchRepository.save(intrvuReqAttch);
        return ResponseEntity.created(new URI("/api/intrvu-req-attches/" + intrvuReqAttch.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, intrvuReqAttch.getId().toString()))
            .body(intrvuReqAttch);
    }

    /**
     * {@code PUT  /intrvu-req-attches/:id} : Updates an existing intrvuReqAttch.
     *
     * @param id the id of the intrvuReqAttch to save.
     * @param intrvuReqAttch the intrvuReqAttch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrvuReqAttch,
     * or with status {@code 400 (Bad Request)} if the intrvuReqAttch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intrvuReqAttch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntrvuReqAttch> updateIntrvuReqAttch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrvuReqAttch intrvuReqAttch
    ) throws URISyntaxException {
        LOG.debug("REST request to update IntrvuReqAttch : {}, {}", id, intrvuReqAttch);
        if (intrvuReqAttch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrvuReqAttch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrvuReqAttchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        intrvuReqAttch = intrvuReqAttchRepository.save(intrvuReqAttch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intrvuReqAttch.getId().toString()))
            .body(intrvuReqAttch);
    }

    /**
     * {@code PATCH  /intrvu-req-attches/:id} : Partial updates given fields of an existing intrvuReqAttch, field will ignore if it is null
     *
     * @param id the id of the intrvuReqAttch to save.
     * @param intrvuReqAttch the intrvuReqAttch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intrvuReqAttch,
     * or with status {@code 400 (Bad Request)} if the intrvuReqAttch is not valid,
     * or with status {@code 404 (Not Found)} if the intrvuReqAttch is not found,
     * or with status {@code 500 (Internal Server Error)} if the intrvuReqAttch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IntrvuReqAttch> partialUpdateIntrvuReqAttch(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IntrvuReqAttch intrvuReqAttch
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IntrvuReqAttch partially : {}, {}", id, intrvuReqAttch);
        if (intrvuReqAttch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intrvuReqAttch.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intrvuReqAttchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IntrvuReqAttch> result = intrvuReqAttchRepository
            .findById(intrvuReqAttch.getId())
            .map(existingIntrvuReqAttch -> {
                if (intrvuReqAttch.getAddDate() != null) {
                    existingIntrvuReqAttch.setAddDate(intrvuReqAttch.getAddDate());
                }
                if (intrvuReqAttch.getAttch() != null) {
                    existingIntrvuReqAttch.setAttch(intrvuReqAttch.getAttch());
                }
                if (intrvuReqAttch.getAttchContentType() != null) {
                    existingIntrvuReqAttch.setAttchContentType(intrvuReqAttch.getAttchContentType());
                }

                return existingIntrvuReqAttch;
            })
            .map(intrvuReqAttchRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, intrvuReqAttch.getId().toString())
        );
    }

    /**
     * {@code GET  /intrvu-req-attches} : get all the intrvuReqAttches.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intrvuReqAttches in body.
     */
    @GetMapping("")
    public List<IntrvuReqAttch> getAllIntrvuReqAttches(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get all IntrvuReqAttches");
        if (eagerload) {
            return intrvuReqAttchRepository.findAllWithEagerRelationships();
        } else {
            return intrvuReqAttchRepository.findAll();
        }
    }

    /**
     * {@code GET  /intrvu-req-attches/:id} : get the "id" intrvuReqAttch.
     *
     * @param id the id of the intrvuReqAttch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intrvuReqAttch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntrvuReqAttch> getIntrvuReqAttch(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IntrvuReqAttch : {}", id);
        Optional<IntrvuReqAttch> intrvuReqAttch = intrvuReqAttchRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(intrvuReqAttch);
    }

    /**
     * {@code DELETE  /intrvu-req-attches/:id} : delete the "id" intrvuReqAttch.
     *
     * @param id the id of the intrvuReqAttch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntrvuReqAttch(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IntrvuReqAttch : {}", id);
        intrvuReqAttchRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
