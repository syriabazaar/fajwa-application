package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import ae.gov.dubaipolice.fajwa.repository.StandardReqAttachRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardReqAttach}.
 */
@Service
@Transactional
public class StandardReqAttachService {

    private static final Logger LOG = LoggerFactory.getLogger(StandardReqAttachService.class);

    private final StandardReqAttachRepository standardReqAttachRepository;

    public StandardReqAttachService(StandardReqAttachRepository standardReqAttachRepository) {
        this.standardReqAttachRepository = standardReqAttachRepository;
    }

    /**
     * Save a standardReqAttach.
     *
     * @param standardReqAttach the entity to save.
     * @return the persisted entity.
     */
    public StandardReqAttach save(StandardReqAttach standardReqAttach) {
        LOG.debug("Request to save StandardReqAttach : {}", standardReqAttach);
        return standardReqAttachRepository.save(standardReqAttach);
    }

    /**
     * Update a standardReqAttach.
     *
     * @param standardReqAttach the entity to save.
     * @return the persisted entity.
     */
    public StandardReqAttach update(StandardReqAttach standardReqAttach) {
        LOG.debug("Request to update StandardReqAttach : {}", standardReqAttach);
        return standardReqAttachRepository.save(standardReqAttach);
    }

    /**
     * Partially update a standardReqAttach.
     *
     * @param standardReqAttach the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StandardReqAttach> partialUpdate(StandardReqAttach standardReqAttach) {
        LOG.debug("Request to partially update StandardReqAttach : {}", standardReqAttach);

        return standardReqAttachRepository
            .findById(standardReqAttach.getId())
            .map(existingStandardReqAttach -> {
                if (standardReqAttach.getName() != null) {
                    existingStandardReqAttach.setName(standardReqAttach.getName());
                }
                if (standardReqAttach.getAttDesc() != null) {
                    existingStandardReqAttach.setAttDesc(standardReqAttach.getAttDesc());
                }

                return existingStandardReqAttach;
            })
            .map(standardReqAttachRepository::save);
    }

    /**
     * Get one standardReqAttach by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StandardReqAttach> findOne(Long id) {
        LOG.debug("Request to get StandardReqAttach : {}", id);
        return standardReqAttachRepository.findById(id);
    }

    /**
     * Delete the standardReqAttach by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete StandardReqAttach : {}", id);
        standardReqAttachRepository.deleteById(id);
    }
}
