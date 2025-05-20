package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.JobVacant;
import ae.gov.dubaipolice.fajwa.repository.JobVacantRepository;
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
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.JobVacant}.
 */
@RestController
@RequestMapping("/api/job-vacants")
@Transactional
public class JobVacantResource {

    private static final Logger LOG = LoggerFactory.getLogger(JobVacantResource.class);

    private static final String ENTITY_NAME = "jobVacant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobVacantRepository jobVacantRepository;

    public JobVacantResource(JobVacantRepository jobVacantRepository) {
        this.jobVacantRepository = jobVacantRepository;
    }

    /**
     * {@code POST  /job-vacants} : Create a new jobVacant.
     *
     * @param jobVacant the jobVacant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobVacant, or with status {@code 400 (Bad Request)} if the jobVacant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JobVacant> createJobVacant(@RequestBody JobVacant jobVacant) throws URISyntaxException {
        LOG.debug("REST request to save JobVacant : {}", jobVacant);
        if (jobVacant.getId() != null) {
            throw new BadRequestAlertException("A new jobVacant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jobVacant = jobVacantRepository.save(jobVacant);
        return ResponseEntity.created(new URI("/api/job-vacants/" + jobVacant.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, jobVacant.getId().toString()))
            .body(jobVacant);
    }

    /**
     * {@code PUT  /job-vacants/:id} : Updates an existing jobVacant.
     *
     * @param id the id of the jobVacant to save.
     * @param jobVacant the jobVacant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobVacant,
     * or with status {@code 400 (Bad Request)} if the jobVacant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobVacant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobVacant> updateJobVacant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobVacant jobVacant
    ) throws URISyntaxException {
        LOG.debug("REST request to update JobVacant : {}, {}", id, jobVacant);
        if (jobVacant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobVacant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobVacantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jobVacant = jobVacantRepository.save(jobVacant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobVacant.getId().toString()))
            .body(jobVacant);
    }

    /**
     * {@code PATCH  /job-vacants/:id} : Partial updates given fields of an existing jobVacant, field will ignore if it is null
     *
     * @param id the id of the jobVacant to save.
     * @param jobVacant the jobVacant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobVacant,
     * or with status {@code 400 (Bad Request)} if the jobVacant is not valid,
     * or with status {@code 404 (Not Found)} if the jobVacant is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobVacant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobVacant> partialUpdateJobVacant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobVacant jobVacant
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update JobVacant partially : {}, {}", id, jobVacant);
        if (jobVacant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobVacant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobVacantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobVacant> result = jobVacantRepository.findById(jobVacant.getId()).map(jobVacantRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobVacant.getId().toString())
        );
    }

    /**
     * {@code GET  /job-vacants} : get all the jobVacants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobVacants in body.
     */
    @GetMapping("")
    public List<JobVacant> getAllJobVacants(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all JobVacants");
        if (eagerload) {
            return jobVacantRepository.findAllWithEagerRelationships();
        } else {
            return jobVacantRepository.findAll();
        }
    }

    /**
     * {@code GET  /job-vacants/:id} : get the "id" jobVacant.
     *
     * @param id the id of the jobVacant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobVacant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobVacant> getJobVacant(@PathVariable("id") Long id) {
        LOG.debug("REST request to get JobVacant : {}", id);
        Optional<JobVacant> jobVacant = jobVacantRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(jobVacant);
    }

    /**
     * {@code DELETE  /job-vacants/:id} : delete the "id" jobVacant.
     *
     * @param id the id of the jobVacant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobVacant(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete JobVacant : {}", id);
        jobVacantRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
