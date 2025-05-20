package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.Nomination;
import ae.gov.dubaipolice.fajwa.repository.NominationRepository;
import ae.gov.dubaipolice.fajwa.service.NominationQueryService;
import ae.gov.dubaipolice.fajwa.service.NominationService;
import ae.gov.dubaipolice.fajwa.service.criteria.NominationCriteria;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.Nomination}.
 */
@RestController
@RequestMapping("/api/nominations")
public class NominationResource {

    private static final Logger LOG = LoggerFactory.getLogger(NominationResource.class);

    private static final String ENTITY_NAME = "nomination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NominationService nominationService;

    private final NominationRepository nominationRepository;

    private final NominationQueryService nominationQueryService;

    public NominationResource(
        NominationService nominationService,
        NominationRepository nominationRepository,
        NominationQueryService nominationQueryService
    ) {
        this.nominationService = nominationService;
        this.nominationRepository = nominationRepository;
        this.nominationQueryService = nominationQueryService;
    }

    /**
     * {@code POST  /nominations} : Create a new nomination.
     *
     * @param nomination the nomination to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nomination, or with status {@code 400 (Bad Request)} if the nomination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nomination> createNomination(@RequestBody Nomination nomination) throws URISyntaxException {
        LOG.debug("REST request to save Nomination : {}", nomination);
        if (nomination.getId() != null) {
            throw new BadRequestAlertException("A new nomination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nomination = nominationService.save(nomination);
        return ResponseEntity.created(new URI("/api/nominations/" + nomination.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, nomination.getId().toString()))
            .body(nomination);
    }

    /**
     * {@code PUT  /nominations/:id} : Updates an existing nomination.
     *
     * @param id the id of the nomination to save.
     * @param nomination the nomination to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nomination,
     * or with status {@code 400 (Bad Request)} if the nomination is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nomination couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nomination> updateNomination(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nomination nomination
    ) throws URISyntaxException {
        LOG.debug("REST request to update Nomination : {}, {}", id, nomination);
        if (nomination.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nomination.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nominationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nomination = nominationService.update(nomination);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nomination.getId().toString()))
            .body(nomination);
    }

    /**
     * {@code PATCH  /nominations/:id} : Partial updates given fields of an existing nomination, field will ignore if it is null
     *
     * @param id the id of the nomination to save.
     * @param nomination the nomination to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nomination,
     * or with status {@code 400 (Bad Request)} if the nomination is not valid,
     * or with status {@code 404 (Not Found)} if the nomination is not found,
     * or with status {@code 500 (Internal Server Error)} if the nomination couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nomination> partialUpdateNomination(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nomination nomination
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Nomination partially : {}, {}", id, nomination);
        if (nomination.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nomination.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nominationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nomination> result = nominationService.partialUpdate(nomination);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, nomination.getId().toString())
        );
    }

    /**
     * {@code GET  /nominations} : get all the nominations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nominations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Nomination>> getAllNominations(
        NominationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Nominations by criteria: {}", criteria);

        Page<Nomination> page = nominationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nominations/count} : count all the nominations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countNominations(NominationCriteria criteria) {
        LOG.debug("REST request to count Nominations by criteria: {}", criteria);
        return ResponseEntity.ok().body(nominationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nominations/:id} : get the "id" nomination.
     *
     * @param id the id of the nomination to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nomination, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nomination> getNomination(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Nomination : {}", id);
        Optional<Nomination> nomination = nominationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nomination);
    }

    /**
     * {@code DELETE  /nominations/:id} : delete the "id" nomination.
     *
     * @param id the id of the nomination to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNomination(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Nomination : {}", id);
        nominationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
