package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.Standarditem;
import ae.gov.dubaipolice.fajwa.repository.StandarditemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.Standarditem}.
 */
@Service
@Transactional
public class StandarditemService {

    private static final Logger LOG = LoggerFactory.getLogger(StandarditemService.class);

    private final StandarditemRepository standarditemRepository;

    public StandarditemService(StandarditemRepository standarditemRepository) {
        this.standarditemRepository = standarditemRepository;
    }

    /**
     * Save a standarditem.
     *
     * @param standarditem the entity to save.
     * @return the persisted entity.
     */
    public Standarditem save(Standarditem standarditem) {
        LOG.debug("Request to save Standarditem : {}", standarditem);
        return standarditemRepository.save(standarditem);
    }

    /**
     * Update a standarditem.
     *
     * @param standarditem the entity to save.
     * @return the persisted entity.
     */
    public Standarditem update(Standarditem standarditem) {
        LOG.debug("Request to update Standarditem : {}", standarditem);
        return standarditemRepository.save(standarditem);
    }

    /**
     * Partially update a standarditem.
     *
     * @param standarditem the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Standarditem> partialUpdate(Standarditem standarditem) {
        LOG.debug("Request to partially update Standarditem : {}", standarditem);

        return standarditemRepository
            .findById(standarditem.getId())
            .map(existingStandarditem -> {
                if (standarditem.getName() != null) {
                    existingStandarditem.setName(standarditem.getName());
                }
                if (standarditem.getIsActive() != null) {
                    existingStandarditem.setIsActive(standarditem.getIsActive());
                }
                if (standarditem.getWeightPercentage() != null) {
                    existingStandarditem.setWeightPercentage(standarditem.getWeightPercentage());
                }

                return existingStandarditem;
            })
            .map(standarditemRepository::save);
    }

    /**
     * Get all the standarditems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Standarditem> findAllWithEagerRelationships(Pageable pageable) {
        return standarditemRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one standarditem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Standarditem> findOne(Long id) {
        LOG.debug("Request to get Standarditem : {}", id);
        return standarditemRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the standarditem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Standarditem : {}", id);
        standarditemRepository.deleteById(id);
    }
}
