package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.StandardReqAttach;
import ae.gov.dubaipolice.fajwa.repository.StandardReqAttachRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.StandardReqAttachCriteria;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link StandardReqAttach} entities in the database.
 * The main input is a {@link StandardReqAttachCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StandardReqAttach} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StandardReqAttachQueryService extends QueryService<StandardReqAttach> {

    private static final Logger LOG = LoggerFactory.getLogger(StandardReqAttachQueryService.class);

    private final StandardReqAttachRepository standardReqAttachRepository;

    public StandardReqAttachQueryService(StandardReqAttachRepository standardReqAttachRepository) {
        this.standardReqAttachRepository = standardReqAttachRepository;
    }

    /**
     * Return a {@link List} of {@link StandardReqAttach} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StandardReqAttach> findByCriteria(StandardReqAttachCriteria criteria) {
        LOG.debug("find by criteria : {}", criteria);
        final Specification<StandardReqAttach> specification = createSpecification(criteria);
        return standardReqAttachRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StandardReqAttachCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<StandardReqAttach> specification = createSpecification(criteria);
        return standardReqAttachRepository.count(specification);
    }

    /**
     * Function to convert {@link StandardReqAttachCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StandardReqAttach> createSpecification(StandardReqAttachCriteria criteria) {
        Specification<StandardReqAttach> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), StandardReqAttach_.id),
                buildStringSpecification(criteria.getName(), StandardReqAttach_.name),
                buildStringSpecification(criteria.getAttDesc(), StandardReqAttach_.attDesc)
            );
        }
        return specification;
    }
}
