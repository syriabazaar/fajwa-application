package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.Nomination;
import ae.gov.dubaipolice.fajwa.repository.NominationRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.NominationCriteria;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Nomination} entities in the database.
 * The main input is a {@link NominationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Nomination} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NominationQueryService extends QueryService<Nomination> {

    private static final Logger LOG = LoggerFactory.getLogger(NominationQueryService.class);

    private final NominationRepository nominationRepository;

    public NominationQueryService(NominationRepository nominationRepository) {
        this.nominationRepository = nominationRepository;
    }

    /**
     * Return a {@link Page} of {@link Nomination} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Nomination> findByCriteria(NominationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nomination> specification = createSpecification(criteria);
        return nominationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NominationCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Nomination> specification = createSpecification(criteria);
        return nominationRepository.count(specification);
    }

    /**
     * Function to convert {@link NominationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nomination> createSpecification(NominationCriteria criteria) {
        Specification<Nomination> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Nomination_.id),
                buildRangeSpecification(criteria.getMachPerc(), Nomination_.machPerc),
                buildSpecification(criteria.getEmployeeId(), root -> root.join(Nomination_.employee, JoinType.LEFT).get(Employee_.id)),
                buildSpecification(criteria.getJobVacantId(), root -> root.join(Nomination_.jobVacant, JoinType.LEFT).get(JobVacant_.id)),
                buildSpecification(criteria.getAppointmentId(), root ->
                    root.join(Nomination_.appointment, JoinType.LEFT).get(Appointment_.id)
                )
            );
        }
        return specification;
    }
}
