package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.StandardType;
import ae.gov.dubaipolice.fajwa.repository.StandardTypeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardType}.
 */
@Service
@Transactional
public class StandardTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(StandardTypeService.class);

    private final StandardTypeRepository standardTypeRepository;

    public StandardTypeService(StandardTypeRepository standardTypeRepository) {
        this.standardTypeRepository = standardTypeRepository;
    }

    /**
     * Save a standardType.
     *
     * @param standardType the entity to save.
     * @return the persisted entity.
     */
    public StandardType save(StandardType standardType) {
        LOG.debug("Request to save StandardType : {}", standardType);
        return standardTypeRepository.save(standardType);
    }

    /**
     * Update a standardType.
     *
     * @param standardType the entity to save.
     * @return the persisted entity.
     */
    public StandardType update(StandardType standardType) {
        LOG.debug("Request to update StandardType : {}", standardType);
        return standardTypeRepository.save(standardType);
    }

    /**
     * Partially update a standardType.
     *
     * @param standardType the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StandardType> partialUpdate(StandardType standardType) {
        LOG.debug("Request to partially update StandardType : {}", standardType);

        return standardTypeRepository
            .findById(standardType.getId())
            .map(existingStandardType -> {
                if (standardType.getName() != null) {
                    existingStandardType.setName(standardType.getName());
                }

                return existingStandardType;
            })
            .map(standardTypeRepository::save);
    }

    /**
     * Get one standardType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StandardType> findOne(Long id) {
        LOG.debug("Request to get StandardType : {}", id);
        return standardTypeRepository.findById(id);
    }

    /**
     * Delete the standardType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete StandardType : {}", id);
        standardTypeRepository.deleteById(id);
    }
}
