package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.Standarditem;
import ae.gov.dubaipolice.fajwa.repository.StandarditemRepository;
import ae.gov.dubaipolice.fajwa.service.StandarditemQueryService;
import ae.gov.dubaipolice.fajwa.service.StandarditemService;
import ae.gov.dubaipolice.fajwa.service.criteria.StandarditemCriteria;
import ae.gov.dubaipolice.fajwa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.Standarditem}.
 */
@RestController
@RequestMapping("/api/standarditems")
public class StandarditemResource {

    private static final Logger LOG = LoggerFactory.getLogger(StandarditemResource.class);

    private static final String ENTITY_NAME = "standarditem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandarditemService standarditemService;

    private final StandarditemRepository standarditemRepository;

    private final StandarditemQueryService standarditemQueryService;

    public StandarditemResource(
        StandarditemService standarditemService,
        StandarditemRepository standarditemRepository,
        StandarditemQueryService standarditemQueryService
    ) {
        this.standarditemService = standarditemService;
        this.standarditemRepository = standarditemRepository;
        this.standarditemQueryService = standarditemQueryService;
    }

    /**
     * {@code POST  /standarditems} : Create a new standarditem.
     *
     * @param standarditem the standarditem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new standarditem, or with status {@code 400 (Bad Request)} if the standarditem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Standarditem> createStandarditem(@RequestBody Standarditem standarditem) throws URISyntaxException {
        LOG.debug("REST request to save Standarditem : {}", standarditem);
        if (standarditem.getId() != null) {
            throw new BadRequestAlertException("A new standarditem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        standarditem = standarditemService.save(standarditem);
        return ResponseEntity.created(new URI("/api/standarditems/" + standarditem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, standarditem.getId().toString()))
            .body(standarditem);
    }

    /**
     * {@code PUT  /standarditems/:id} : Updates an existing standarditem.
     *
     * @param id the id of the standarditem to save.
     * @param standarditem the standarditem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standarditem,
     * or with status {@code 400 (Bad Request)} if the standarditem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the standarditem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Standarditem> updateStandarditem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Standarditem standarditem
    ) throws URISyntaxException {
        LOG.debug("REST request to update Standarditem : {}, {}", id, standarditem);
        if (standarditem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standarditem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standarditemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        standarditem = standarditemService.update(standarditem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standarditem.getId().toString()))
            .body(standarditem);
    }

    /**
     * {@code PATCH  /standarditems/:id} : Partial updates given fields of an existing standarditem, field will ignore if it is null
     *
     * @param id the id of the standarditem to save.
     * @param standarditem the standarditem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated standarditem,
     * or with status {@code 400 (Bad Request)} if the standarditem is not valid,
     * or with status {@code 404 (Not Found)} if the standarditem is not found,
     * or with status {@code 500 (Internal Server Error)} if the standarditem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Standarditem> partialUpdateStandarditem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Standarditem standarditem
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Standarditem partially : {}, {}", id, standarditem);
        if (standarditem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, standarditem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!standarditemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Standarditem> result = standarditemService.partialUpdate(standarditem);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, standarditem.getId().toString())
        );
    }

    /**
     * {@code GET  /standarditems} : get all the standarditems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of standarditems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Standarditem>> getAllStandarditems(
        StandarditemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Standarditems by criteria: {}", criteria);

        Page<Standarditem> page = standarditemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /standarditems/count} : count all the standarditems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStandarditems(StandarditemCriteria criteria) {
        LOG.debug("REST request to count Standarditems by criteria: {}", criteria);
        return ResponseEntity.ok().body(standarditemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /standarditems/:id} : get the "id" standarditem.
     *
     * @param id the id of the standarditem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the standarditem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Standarditem> getStandarditem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Standarditem : {}", id);
        Optional<Standarditem> standarditem = standarditemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(standarditem);
    }

    /**
     * {@code DELETE  /standarditems/:id} : delete the "id" standarditem.
     *
     * @param id the id of the standarditem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStandarditem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Standarditem : {}", id);
        standarditemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
