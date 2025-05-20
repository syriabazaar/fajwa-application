package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.StandardType;
import ae.gov.dubaipolice.fajwa.repository.StandardTypeRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardTypeCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StandardType} entities in the database.
 * The main input is a {@link StandardTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StandardType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StandardTypeQueryService extends QueryService<StandardType> {

    private static final Logger LOG = LoggerFactory.getLogger(StandardTypeQueryService.class);

    private final StandardTypeRepository standardTypeRepository;

    public StandardTypeQueryService(StandardTypeRepository standardTypeRepository) {
        this.standardTypeRepository = standardTypeRepository;
    }

    /**
     * Return a {@link List} of {@link StandardType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StandardType> findByCriteria(StandardTypeCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<StandardType> specification = createSpecification(criteria);
        return standardTypeRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StandardTypeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<StandardType> specification = createSpecification(criteria);
        return standardTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link StandardTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StandardType> createSpecification(StandardTypeCriteria criteria) {
        Specification<StandardType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), StandardType_.id),
                buildStringSpecification(criteria.getName(), StandardType_.name),
                buildSpecification(criteria.getJobDescId(), root -> root.join(StandardType_.jobDescs, JoinType.LEFT).get(JobDesc_.id))
            );
        }
        return specification;
    }
}
