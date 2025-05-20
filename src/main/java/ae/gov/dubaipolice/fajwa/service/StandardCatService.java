package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import ae.gov.dubaipolice.fajwa.repository.StandardCatRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.StandardCat}.
 */
@Service
@Transactional
public class StandardCatService {

    private static final Logger LOG = LoggerFactory.getLogger(StandardCatService.class);

    private final StandardCatRepository standardCatRepository;

    public StandardCatService(StandardCatRepository standardCatRepository) {
        this.standardCatRepository = standardCatRepository;
    }

    /**
     * Save a standardCat.
     *
     * @param standardCat the entity to save.
     * @return the persisted entity.
     */
    public StandardCat save(StandardCat standardCat) {
        LOG.debug("Request to save StandardCat : {}", standardCat);
        return standardCatRepository.save(standardCat);
    }

    /**
     * Update a standardCat.
     *
     * @param standardCat the entity to save.
     * @return the persisted entity.
     */
    public StandardCat update(StandardCat standardCat) {
        LOG.debug("Request to update StandardCat : {}", standardCat);
        return standardCatRepository.save(standardCat);
    }

    /**
     * Partially update a standardCat.
     *
     * @param standardCat the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StandardCat> partialUpdate(StandardCat standardCat) {
        LOG.debug("Request to partially update StandardCat : {}", standardCat);

        return standardCatRepository
            .findById(standardCat.getId())
            .map(existingStandardCat -> {
                if (standardCat.getName() != null) {
                    existingStandardCat.setName(standardCat.getName());
                }

                return existingStandardCat;
            })
            .map(standardCatRepository::save);
    }

    /**
     * Get all the standardCats with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StandardCat> findAllWithEagerRelationships(Pageable pageable) {
        return standardCatRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one standardCat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StandardCat> findOne(Long id) {
        LOG.debug("Request to get StandardCat : {}", id);
        return standardCatRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the standardCat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete StandardCat : {}", id);
        standardCatRepository.deleteById(id);
    }
}
