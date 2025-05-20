package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.StandardParent;
import ae.gov.dubaipolice.fajwa.repository.StandardParentRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardParentCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StandardParent} entities in the database.
 * The main input is a {@link StandardParentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StandardParent} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StandardParentQueryService extends QueryService<StandardParent> {

    private static final Logger LOG = LoggerFactory.getLogger(StandardParentQueryService.class);

    private final StandardParentRepository standardParentRepository;

    public StandardParentQueryService(StandardParentRepository standardParentRepository) {
        this.standardParentRepository = standardParentRepository;
    }

    /**
     * Return a {@link List} of {@link StandardParent} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StandardParent> findByCriteria(StandardParentCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<StandardParent> specification = createSpecification(criteria);
        return standardParentRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StandardParentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<StandardParent> specification = createSpecification(criteria);
        return standardParentRepository.count(specification);
    }

    /**
     * Function to convert {@link StandardParentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StandardParent> createSpecification(StandardParentCriteria criteria) {
        Specification<StandardParent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), StandardParent_.id),
                buildStringSpecification(criteria.getName(), StandardParent_.name),
                buildSpecification(criteria.getStandardTypeId(), root ->
                    root.join(StandardParent_.standardType, JoinType.LEFT).get(StandardType_.id)
                )
            );
        }
        return specification;
    }
}
