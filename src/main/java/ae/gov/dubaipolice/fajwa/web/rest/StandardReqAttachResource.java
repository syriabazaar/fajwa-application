package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import ae.gov.dubaipolice.fajwa.repository.StandardReqAttachRepository;
import ae.gov.dubaipolice.fajwa.service.StandardReqAttachQueryService;
import ae.gov.dubaipolice.fajwa.service.StandardReqAttachService;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardReqAttachCriteria;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardReqAttach}.
 */
@RestController
@RequestMapping("/api/standard-req-attaches")
public class StandardReqAttachResource {

    private static final Logger LOG = LoggerFactory.getLogger(StandardReqAttachResource.class);

    private static final String ENTITY_NAME = "standardReqAttach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandardReqAttachService standardReqAttachService;

    private final StandardReqAttachRepository standardReqAttachRepository;

    private final StandardReqAttachQueryService standardReqAttachQueryService;

    public StandardReqAttachResource(
        StandardReqAttachService standardReqAttachService,
        StandardReqAttachRepository standardReqAttachRepository,
        StandardReqAttachQueryService standardReqAttachQueryService
    ) {
        this.standardReqAttachService = standardReqAttachService;
        this.standardReqAttachRepository = standardReqAttachRepository;
        this.standardReqAttachQueryService = standardReqAttachQueryService;
    }

    /**
     * {@code POST  /standard-req-attaches} : Create a new standardReqAttach.
     *
     * @param standardReqAttach the standardReqAttach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standardReqAttach, or with status {@code 400 (Bad Request)} if the standardReqAttach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StandardReqAttach> createStandardReqAttach(@RequestBody StandardReqAttach standardReqAttach)
        throws URISyntaxException {
        LOG.debug("REST request to save StandardReqAttach : {}", standardReqAttach);
        if (standardReqAttach.getId() != null) {
            throw new BadRequestAlertException("A new standardReqAttach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standardReqAttach = standardReqAttachService.save(standardReqAttach);
        return ResponseEntity.created(new URI("/api/standard-req-attaches/" + standardReqAttach.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standardReqAttach.getId().toString()))
            .body(standardReqAttach);
    }

    /**
     * {@code PUT  /standard-req-attaches/:id} : Updates an existing standardReqAttach.
     *
     * @param id the id of the standardReqAttach to save.
     * @param standardReqAttach the standardReqAttach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardReqAttach,
     * or with status {@code 400 (Bad Request)} if the standardReqAttach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standardReqAttach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StandardReqAttach> updateStandardReqAttach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardReqAttach standardReqAttach
    ) throws URISyntaxException {
        LOG.debug("REST request to update StandardReqAttach : {}, {}", id, standardReqAttach);
        if (standardReqAttach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardReqAttach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardReqAttachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standardReqAttach = standardReqAttachService.update(standardReqAttach);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardReqAttach.getId().toString()))
            .body(standardReqAttach);
    }

    /**
     * {@code PATCH  /standard-req-attaches/:id} : Partial updates given fields of an existing standardReqAttach, field will ignore if it is null
     *
     * @param id the id of the standardReqAttach to save.
     * @param standardReqAttach the standardReqAttach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardReqAttach,
     * or with status {@code 400 (Bad Request)} if the standardReqAttach is not valid,
     * or with status {@code 404 (Not Found)} if the standardReqAttach is not found,
     * or with status {@code 500 (Internal Server Error)} if the standardReqAttach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StandardReqAttach> partialUpdateStandardReqAttach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardReqAttach standardReqAttach
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StandardReqAttach partially : {}, {}", id, standardReqAttach);
        if (standardReqAttach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardReqAttach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardReqAttachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StandardReqAttach> result = standardReqAttachService.partialUpdate(standardReqAttach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardReqAttach.getId().toString())
        );
    }

    /**
     * {@code GET  /standard-req-attaches} : get all the standardReqAttaches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standardReqAttaches in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StandardReqAttach>> getAllStandardReqAttaches(StandardReqAttachCriteria criteria) {
        LOG.debug("REST request to get StandardReqAttaches by criteria: {}", criteria);

        List<StandardReqAttach> entityList = standardReqAttachQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /standard-req-attaches/count} : count all the standardReqAttaches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStandardReqAttaches(StandardReqAttachCriteria criteria) {
        LOG.debug("REST request to count StandardReqAttaches by criteria: {}", criteria);
        return ResponseEntity.ok().body(standardReqAttachQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /standard-req-attaches/:id} : get the "id" standardReqAttach.
     *
     * @param id the id of the standardReqAttach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standardReqAttach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StandardReqAttach> getStandardReqAttach(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StandardReqAttach : {}", id);
        Optional<StandardReqAttach> standardReqAttach = standardReqAttachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(standardReqAttach);
    }

    /**
     * {@code DELETE  /standard-req-attaches/:id} : delete the "id" standardReqAttach.
     *
     * @param id the id of the standardReqAttach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandardReqAttach(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StandardReqAttach : {}", id);
        standardReqAttachService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
