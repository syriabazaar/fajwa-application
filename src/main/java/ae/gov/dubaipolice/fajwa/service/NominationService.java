package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.Nomination;
import ae.gov.dubaipolice.fajwa.repository.NominationRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.Nomination}.
 */
@Service
@Transactional
public class NominationService {

    private static final Logger LOG = LoggerFactory.getLogger(NominationService.class);

    private final NominationRepository nominationRepository;

    public NominationService(NominationRepository nominationRepository) {
        this.nominationRepository = nominationRepository;
    }

    /**
     * Save a nomination.
     *
     * @param nomination the entity to save.
     * @return the persisted entity.
     */
    public Nomination save(Nomination nomination) {
        LOG.debug("Request to save Nomination : {}", nomination);
        return nominationRepository.save(nomination);
    }

    /**
     * Update a nomination.
     *
     * @param nomination the entity to save.
     * @return the persisted entity.
     */
    public Nomination update(Nomination nomination) {
        LOG.debug("Request to update Nomination : {}", nomination);
        return nominationRepository.save(nomination);
    }

    /**
     * Partially update a nomination.
     *
     * @param nomination the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Nomination> partialUpdate(Nomination nomination) {
        LOG.debug("Request to partially update Nomination : {}", nomination);

        return nominationRepository
            .findById(nomination.getId())
            .map(existingNomination -> {
                if (nomination.getMachPerc() != null) {
                    existingNomination.setMachPerc(nomination.getMachPerc());
                }

                return existingNomination;
            })
            .map(nominationRepository::save);
    }

    /**
     * Get all the nominations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Nomination> findAllWithEagerRelationships(Pageable pageable) {
        return nominationRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the nominations where Appointment is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Nomination> findAllWhereAppointmentIsNull() {
        LOG.debug("Request to get all nominations where Appointment is null");
        return StreamSupport.stream(nominationRepository.findAll().spliterator(), false)
            .filter(nomination -> nomination.getAppointment() == null)
            .toList();
    }

    /**
     * Get one nomination by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Nomination> findOne(Long id) {
        LOG.debug("Request to get Nomination : {}", id);
        return nominationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the nomination by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Nomination : {}", id);
        nominationRepository.deleteById(id);
    }
}
