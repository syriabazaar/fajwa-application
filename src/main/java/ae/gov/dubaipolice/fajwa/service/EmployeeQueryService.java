package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.*; // for static metamodels
import ae.gov.dubaipolice.fajwa.domain.Employee;
import ae.gov.dubaipolice.fajwa.repository.EmployeeRepository;
import ae.gov.dubaipolice.fajwa.service.criteria.EmployeeCriteria;
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
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Employee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeQueryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Return a {@link Page} of {@link Employee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Employee> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Employee_.id),
                buildStringSpecification(criteria.getFullname(), Employee_.fullname),
                buildRangeSpecification(criteria.getGradeId(), Employee_.gradeId),
                buildStringSpecification(criteria.getGradeName(), Employee_.gradeName),
                buildStringSpecification(criteria.getHomePhone(), Employee_.homePhone),
                buildStringSpecification(criteria.getMobileNumber(), Employee_.mobileNumber),
                buildRangeSpecification(criteria.getAssigmentId(), Employee_.assigmentId),
                buildStringSpecification(criteria.getDateOfAssignment(), Employee_.dateOfAssignment),
                buildStringSpecification(criteria.getJobName(), Employee_.jobName),
                buildStringSpecification(criteria.getAddress(), Employee_.address),
                buildRangeSpecification(criteria.getOrganizationId(), Employee_.organizationId),
                buildStringSpecification(criteria.getOrganization(), Employee_.organization),
                buildStringSpecification(criteria.getNationalIdentifier(), Employee_.nationalIdentifier),
                buildStringSpecification(criteria.getUid(), Employee_.uid),
                buildStringSpecification(criteria.getParentDepartmentId(), Employee_.parentDepartmentId),
                buildRangeSpecification(criteria.getSlmMilitaryFlag(), Employee_.slmMilitaryFlag),
                buildStringSpecification(criteria.getMilitaryFirstName(), Employee_.militaryFirstName),
                buildStringSpecification(criteria.getSlmName(), Employee_.slmName),
                buildRangeSpecification(criteria.getAge(), Employee_.age),
                buildRangeSpecification(criteria.getDateOfHire(), Employee_.dateOfHire),
                buildRangeSpecification(criteria.getDateOfLastHire(), Employee_.dateOfLastHire),
                buildStringSpecification(criteria.getGender(), Employee_.gender),
                buildRangeSpecification(criteria.getJobId(), Employee_.jobId),
                buildStringSpecification(criteria.getNationalityCode(), Employee_.nationalityCode),
                buildStringSpecification(criteria.getNationality(), Employee_.nationality),
                buildRangeSpecification(criteria.getPersonStartDate(), Employee_.personStartDate),
                buildRangeSpecification(criteria.getOriginalDateOfHire(), Employee_.originalDateOfHire),
                buildStringSpecification(criteria.getSupervisorId(), Employee_.supervisorId),
                buildStringSpecification(criteria.getSupervisorFullName(), Employee_.supervisorFullName),
                buildStringSpecification(criteria.getParentDepartmentName(), Employee_.parentDepartmentName),
                buildRangeSpecification(criteria.getSectionId(), Employee_.sectionId),
                buildStringSpecification(criteria.getSectionName(), Employee_.sectionName),
                buildRangeSpecification(criteria.getAdjustedServiceDate(), Employee_.adjustedServiceDate),
                buildStringSpecification(criteria.getQualificationType(), Employee_.qualificationType),
                buildStringSpecification(criteria.getQualificationSpecification(), Employee_.qualificationSpecification),
                buildSpecification(criteria.getIntrvuReqAttchId(), root ->
                    root.join(Employee_.intrvuReqAttches, JoinType.LEFT).get(IntrvuReqAttch_.id)
                )
            );
        }
        return specification;
    }
}
