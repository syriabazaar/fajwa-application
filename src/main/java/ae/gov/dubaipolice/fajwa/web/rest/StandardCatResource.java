package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import ae.gov.dubaipolice.fajwa.repository.StandardCatRepository;
import ae.gov.dubaipolice.fajwa.service.StandardCatQueryService;
import ae.gov.dubaipolice.fajwa.service.StandardCatService;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardCatCriteria;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardCat}.
 */
@RestController
@RequestMapping("/api/standard-cats")
public class StandardCatResource {

    private static final Logger LOG = LoggerFactory.getLogger(StandardCatResource.class);

    private static final String ENTITY_NAME = "standardCat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandardCatService standardCatService;

    private final StandardCatRepository standardCatRepository;

    private final StandardCatQueryService standardCatQueryService;

    public StandardCatResource(
        StandardCatService standardCatService,
        StandardCatRepository standardCatRepository,
        StandardCatQueryService standardCatQueryService
    ) {
        this.standardCatService = standardCatService;
        this.standardCatRepository = standardCatRepository;
        this.standardCatQueryService = standardCatQueryService;
    }

    /**
     * {@code POST  /standard-cats} : Create a new standardCat.
     *
     * @param standardCat the standardCat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standardCat, or with status {@code 400 (Bad Request)} if the standardCat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StandardCat> createStandardCat(@RequestBody StandardCat standardCat) throws URISyntaxException {
        LOG.debug("REST request to save StandardCat : {}", standardCat);
        if (standardCat.getId() != null) {
            throw new BadRequestAlertException("A new standardCat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standardCat = standardCatService.save(standardCat);
        return ResponseEntity.created(new URI("/api/standard-cats/" + standardCat.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standardCat.getId().toString()))
            .body(standardCat);
    }

    /**
     * {@code PUT  /standard-cats/:id} : Updates an existing standardCat.
     *
     * @param id the id of the standardCat to save.
     * @param standardCat the standardCat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardCat,
     * or with status {@code 400 (Bad Request)} if the standardCat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standardCat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StandardCat> updateStandardCat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardCat standardCat
    ) throws URISyntaxException {
        LOG.debug("REST request to update StandardCat : {}, {}", id, standardCat);
        if (standardCat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardCat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardCatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standardCat = standardCatService.update(standardCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardCat.getId().toString()))
            .body(standardCat);
    }

    /**
     * {@code PATCH  /standard-cats/:id} : Partial updates given fields of an existing standardCat, field will ignore if it is null
     *
     * @param id the id of the standardCat to save.
     * @param standardCat the standardCat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standardCat,
     * or with status {@code 400 (Bad Request)} if the standardCat is not valid,
     * or with status {@code 404 (Not Found)} if the standardCat is not found,
     * or with status {@code 500 (Internal Server Error)} if the standardCat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StandardCat> partialUpdateStandardCat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StandardCat standardCat
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update StandardCat partially : {}, {}", id, standardCat);
        if (standardCat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standardCat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standardCatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StandardCat> result = standardCatService.partialUpdate(standardCat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standardCat.getId().toString())
        );
    }

    /**
     * {@code GET  /standard-cats} : get all the standardCats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standardCats in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StandardCat>> getAllStandardCats(StandardCatCriteria criteria) {
        LOG.debug("REST request to get StandardCats by criteria: {}", criteria);

        List<StandardCat> entityList = standardCatQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /standard-cats/count} : count all the standardCats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStandardCats(StandardCatCriteria criteria) {
        LOG.debug("REST request to count StandardCats by criteria: {}", criteria);
        return ResponseEntity.ok().body(standardCatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /standard-cats/:id} : get the "id" standardCat.
     *
     * @param id the id of the standardCat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standardCat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StandardCat> getStandardCat(@PathVariable("id") Long id) {
        LOG.debug("REST request to get StandardCat : {}", id);
        Optional<StandardCat> standardCat = standardCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(standardCat);
    }

    /**
     * {@code DELETE  /standard-cats/:id} : delete the "id" standardCat.
     *
     * @param id the id of the standardCat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandardCat(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete StandardCat : {}", id);
        standardCatService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
