package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import ae.gov.dubaipolice.fajwa.repository.StandardParentRepository;
import ae.gov.dubaipolice.fajwa.service.StandardParentQueryService;
import ae.gov.dubaipolice.fajwa.service.StandardParentService;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardParentCriteria;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardParent}.
 */
@RestController
@RequestMapping("/api/standard-parents")
public class StandardParentResource {

    private static final Logger LOG = LoggerFactory.getLogger(StandardParentResource.class);

    private static final String ENTITY_NAME = "standardParent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandardParentService standardParentService;

    private final StandardParentRepository standardParentRepository;

    private final StandardParentQueryService standardParentQueryService;

    public StandardParentResource(
        StandardParentService standardParentService,
        StandardParentRepository standardParentRepository,
        StandardParentQueryService standardParentQueryService
    ) {
        this.standardParentService = standardParentService;
        this.standardParentRepository = standardParentRepository;
        this.standardParentQueryService = standardParentQueryService;
    }

    /**
     * {@code POST  /standard-parents} : Create a new standardParent.
     *
     * @param standardParent the standardParent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standardParent, or with status {@code 400 (Bad Request)} if the standardParent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StandardParent> createStandardParent(@RequestBody StandardParent standardParent) throws URISyntaxException {
        LOG.debug("REST request to save StandardParent : {}", standardParent);
        if (standardParent.getId() != null) {
            throw new BadRequestAlertException("A new standardParent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standardParent = standardParentService.save(standardParent);
        return ResponseEntity.created(new URI("/api/standard-parents/" + standardParent.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standardParent.getId().toString()))
            .body(standardParent);
    }

    /**
     * {@code PUT  /standard-parents/:id} : Updates an existing standardParent.
     *
     * @param id the id of the standardParent to save.
     * @param standardParent the standardParent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardParent,
     * or with status {@code 400 (Bad Request)} if the standardParent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standardParent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StandardParent> updateStandardParent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardParent standardParent
    ) throws URISyntaxException {
        LOG.debug("REST request to update StandardParent : {}, {}", id, standardParent);
        if (standardParent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardParent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardParentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standardParent = standardParentService.update(standardParent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardParent.getId().toString()))
            .body(standardParent);
    }

    /**
     * {@code PATCH  /standard-parents/:id} : Partial updates given fields of an existing standardParent, field will ignore if it is null
     *
     * @param id the id of the standardParent to save.
     * @param standardParent the standardParent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardParent,
     * or with status {@code 400 (Bad Request)} if the standardParent is not valid,
     * or with status {@code 404 (Not Found)} if the standardParent is not found,
     * or with status {@code 500 (Internal Server Error)} if the standardParent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StandardParent> partialUpdateStandardParent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardParent standardParent
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StandardParent partially : {}, {}", id, standardParent);
        if (standardParent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardParent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardParentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StandardParent> result = standardParentService.partialUpdate(standardParent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardParent.getId().toString())
        );
    }

    /**
     * {@code GET  /standard-parents} : get all the standardParents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standardParents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StandardParent>> getAllStandardParents(StandardParentCriteria criteria) {
        LOG.debug("REST request to get StandardParents by criteria: {}", criteria);

        List<StandardParent> entityList = standardParentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /standard-parents/count} : count all the standardParents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStandardParents(StandardParentCriteria criteria) {
        LOG.debug("REST request to count StandardParents by criteria: {}", criteria);
        return ResponseEntity.ok().body(standardParentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /standard-parents/:id} : get the "id" standardParent.
     *
     * @param id the id of the standardParent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standardParent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StandardParent> getStandardParent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StandardParent : {}", id);
        Optional<StandardParent> standardParent = standardParentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(standardParent);
    }

    /**
     * {@code DELETE  /standard-parents/:id} : delete the "id" standardParent.
     *
     * @param id the id of the standardParent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandardParent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StandardParent : {}", id);
        standardParentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
