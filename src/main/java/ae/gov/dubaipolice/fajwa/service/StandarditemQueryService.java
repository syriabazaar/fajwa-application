package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.Standarditem;
import ae.gov.dubaipolice.fajwa.repository.StandarditemRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.StandarditemCriteria;
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
 * Service for executing complex queries for {@link Standarditem} entities in the database.
 * The main input is a {@link StandarditemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Standarditem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StandarditemQueryService extends QueryService<Standarditem> {

    private static final Logger LOG = LoggerFactory.getLogger(StandarditemQueryService.class);

    private final StandarditemRepository standarditemRepository;

    public StandarditemQueryService(StandarditemRepository standarditemRepository) {
        this.standarditemRepository = standarditemRepository;
    }

    /**
     * Return a {@link Page} of {@link Standarditem} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Standarditem> findByCriteria(StandarditemCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Standarditem> specification = createSpecification(criteria);
        return standarditemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StandarditemCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Standarditem> specification = createSpecification(criteria);
        return standarditemRepository.count(specification);
    }

    /**
     * Function to convert {@link StandarditemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Standarditem> createSpecification(StandarditemCriteria criteria) {
        Specification<Standarditem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Standarditem_.id),
                buildStringSpecification(criteria.getName(), Standarditem_.name),
                buildSpecification(criteria.getIsActive(), Standarditem_.isActive),
                buildRangeSpecification(criteria.getWeightPercentage(), Standarditem_.weightPercentage),
                buildSpecification(criteria.getStandardCatId(), root ->
                    root.join(Standarditem_.standardCat, JoinType.LEFT).get(StandardCat_.id)
                ),
                buildSpecification(criteria.getIntrvuStrdDtlsId(), root ->
                    root.join(Standarditem_.intrvuStrdDtls, JoinType.LEFT).get(IntrvuStrdDtls_.id)
                )
            );
        }
        return specification;
    }
}
