package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.StandardType;
import ae.gov.dubaipolice.fajwa.repository.StandardTypeRepository;
import ae.gov.dubaipolice.fajwa.service.StandardTypeQueryService;
import ae.gov.dubaipolice.fajwa.service.StandardTypeService;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardTypeCriteria;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardType}.
 */
@RestController
@RequestMapping("/api/standard-types")
public class StandardTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(StandardTypeResource.class);

    private static final String ENTITY_NAME = "standardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandardTypeService standardTypeService;

    private final StandardTypeRepository standardTypeRepository;

    private final StandardTypeQueryService standardTypeQueryService;

    public StandardTypeResource(
        StandardTypeService standardTypeService,
        StandardTypeRepository standardTypeRepository,
        StandardTypeQueryService standardTypeQueryService
    ) {
        this.standardTypeService = standardTypeService;
        this.standardTypeRepository = standardTypeRepository;
        this.standardTypeQueryService = standardTypeQueryService;
    }

    /**
     * {@code POST  /standard-types} : Create a new standardType.
     *
     * @param standardType the standardType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standardType, or with status {@code 400 (Bad Request)} if the standardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StandardType> createStandardType(@RequestBody StandardType standardType) throws URISyntaxException {
        LOG.debug("REST request to save StandardType : {}", standardType);
        if (standardType.getId() != null) {
            throw new BadRequestAlertException("A new standardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standardType = standardTypeService.save(standardType);
        return ResponseEntity.created(new URI("/api/standard-types/" + standardType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standardType.getId().toString()))
            .body(standardType);
    }

    /**
     * {@code PUT  /standard-types/:id} : Updates an existing standardType.
     *
     * @param id the id of the standardType to save.
     * @param standardType the standardType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardType,
     * or with status {@code 400 (Bad Request)} if the standardType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standardType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StandardType> updateStandardType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardType standardType
    ) throws URISyntaxException {
        LOG.debug("REST request to update StandardType : {}, {}", id, standardType);
        if (standardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standardType = standardTypeService.update(standardType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardType.getId().toString()))
            .body(standardType);
    }

    /**
     * {@code PATCH  /standard-types/:id} : Partial updates given fields of an existing standardType, field will ignore if it is null
     *
     * @param id the id of the standardType to save.
     * @param standardType the standardType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardType,
     * or with status {@code 400 (Bad Request)} if the standardType is not valid,
     * or with status {@code 404 (Not Found)} if the standardType is not found,
     * or with status {@code 500 (Internal Server Error)} if the standardType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StandardType> partialUpdateStandardType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardType standardType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StandardType partially : {}, {}", id, standardType);
        if (standardType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StandardType> result = standardTypeService.partialUpdate(standardType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardType.getId().toString())
        );
    }

    /**
     * {@code GET  /standard-types} : get all the standardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standardTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StandardType>> getAllStandardTypes(StandardTypeCriteria criteria) {
        LOG.debug("REST request to get StandardTypes by criteria: {}", criteria);

        List<StandardType> entityList = standardTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /standard-types/count} : count all the standardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStandardTypes(StandardTypeCriteria criteria) {
        LOG.debug("REST request to count StandardTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(standardTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /standard-types/:id} : get the "id" standardType.
     *
     * @param id the id of the standardType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standardType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StandardType> getStandardType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StandardType : {}", id);
        Optional<StandardType> standardType = standardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(standardType);
    }

    /**
     * {@code DELETE  /standard-types/:id} : delete the "id" standardType.
     *
     * @param id the id of the standardType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandardType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StandardType : {}", id);
        standardTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
