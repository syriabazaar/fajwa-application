package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.StandardCat;
import ae.gov.dubaipolice.fajwa.repository.StandardCatRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardCatCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StandardCat} entities in the database.
 * The main input is a {@link StandardCatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StandardCat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StandardCatQueryService extends QueryService<StandardCat> {

    private static final Logger LOG = LoggerFactory.getLogger(StandardCatQueryService.class);

    private final StandardCatRepository standardCatRepository;

    public StandardCatQueryService(StandardCatRepository standardCatRepository) {
        this.standardCatRepository = standardCatRepository;
    }

    /**
     * Return a {@link List} of {@link StandardCat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StandardCat> findByCriteria(StandardCatCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<StandardCat> specification = createSpecification(criteria);
        return standardCatRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StandardCatCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<StandardCat> specification = createSpecification(criteria);
        return standardCatRepository.count(specification);
    }

    /**
     * Function to convert {@link StandardCatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StandardCat> createSpecification(StandardCatCriteria criteria) {
        Specification<StandardCat> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), StandardCat_.id),
                buildStringSpecification(criteria.getName(), StandardCat_.name),
                buildSpecification(criteria.getStandardParentId(), root ->
                    root.join(StandardCat_.standardParent, JoinType.LEFT).get(StandardParent_.id)
                ),
                buildSpecification(criteria.getReqAttachmentId(), root ->
                    root.join(StandardCat_.reqAttachment, JoinType.LEFT).get(StandardReqAttach_.id)
                )
            );
        }
        return specification;
    }
}
