package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import ae.gov.dubaipolice.fajwa.repository.StandardParentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardParent}.
 */
@Service
@Transactional
public class StandardParentService {

    private static final Logger LOG = LoggerFactory.getLogger(StandardParentService.class);

    private final StandardParentRepository standardParentRepository;

    public StandardParentService(StandardParentRepository standardParentRepository) {
        this.standardParentRepository = standardParentRepository;
    }

    /**
     * Save a standardParent.
     *
     * @param standardParent the entity to save.
     * @return the persisted entity.
     */
    public StandardParent save(StandardParent standardParent) {
        LOG.debug("Request to save StandardParent : {}", standardParent);
        return standardParentRepository.save(standardParent);
    }

    /**
     * Update a standardParent.
     *
     * @param standardParent the entity to save.
     * @return the persisted entity.
     */
    public StandardParent update(StandardParent standardParent) {
        LOG.debug("Request to update StandardParent : {}", standardParent);
        return standardParentRepository.save(standardParent);
    }

    /**
     * Partially update a standardParent.
     *
     * @param standardParent the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StandardParent> partialUpdate(StandardParent standardParent) {
        LOG.debug("Request to partially update StandardParent : {}", standardParent);

        return standardParentRepository
            .findById(standardParent.getId())
            .map(existingStandardParent -> {
                if (standardParent.getName() != null) {
                    existingStandardParent.setName(standardParent.getName());
                }

                return existingStandardParent;
            })
            .map(standardParentRepository::save);
    }

    /**
     * Get all the standardParents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StandardParent> findAllWithEagerRelationships(Pageable pageable) {
        return standardParentRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one standardParent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StandardParent> findOne(Long id) {
        LOG.debug("Request to get StandardParent : {}", id);
        return standardParentRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the standardParent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete StandardParent : {}", id);
        standardParentRepository.deleteById(id);
    }
}
