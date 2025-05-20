package ae.gov.dubaipolice.fajwa.web.rest;

import ae.gov.dubaipolice.fajwa.domain.JobDesc;
import ae.gov.dubaipolice.fajwa.repository.JobDescRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ae.gov.dubaipolice.fajwa.domain.JobDesc}.
 */
@RestController
@RequestMapping("/api/job-descs")
@Transactional
public class JobDescResource {

    private static final Logger LOG = LoggerFactory.getLogger(JobDescResource.class);

    private final JobDescRepository jobDescRepository;

    public JobDescResource(JobDescRepository jobDescRepository) {
        this.jobDescRepository = jobDescRepository;
    }

    /**
     * {@code GET  /job-descs} : get all the jobDescs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobDescs in body.
     */
    @GetMapping("")
    public List<JobDesc> getAllJobDescs() {
        LOG.debug("REST request to get all JobDescs");
        return jobDescRepository.findAll();
    }

    /**
     * {@code GET  /job-descs/:id} : get the "id" jobDesc.
     *
     * @param id the id of the jobDesc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobDesc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobDesc> getJobDesc(@PathVariable("id") Long id) {
        LOG.debug("REST request to get JobDesc : {}", id);
        Optional<JobDesc> jobDesc = jobDescRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobDesc);
    }
}
