package ae.gov.dubaipolice.fajwa.service;

import ae.gov.dubaipolice.fajwa.domain.Employee;
import ae.gov.dubaipolice.fajwa.repository.EmployeeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ae.gov.dubaipolice.fajwa.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee save(Employee employee) {
        LOG.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Update a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    public Employee update(Employee employee) {
        LOG.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    /**
     * Partially update a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Employee> partialUpdate(Employee employee) {
        LOG.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getFullname() != null) {
                    existingEmployee.setFullname(employee.getFullname());
                }
                if (employee.getGradeId() != null) {
                    existingEmployee.setGradeId(employee.getGradeId());
                }
                if (employee.getGradeName() != null) {
                    existingEmployee.setGradeName(employee.getGradeName());
                }
                if (employee.getHomePhone() != null) {
                    existingEmployee.setHomePhone(employee.getHomePhone());
                }
                if (employee.getMobileNumber() != null) {
                    existingEmployee.setMobileNumber(employee.getMobileNumber());
                }
                if (employee.getAssigmentId() != null) {
                    existingEmployee.setAssigmentId(employee.getAssigmentId());
                }
                if (employee.getDateOfAssignment() != null) {
                    existingEmployee.setDateOfAssignment(employee.getDateOfAssignment());
                }
                if (employee.getJobName() != null) {
                    existingEmployee.setJobName(employee.getJobName());
                }
                if (employee.getAddress() != null) {
                    existingEmployee.setAddress(employee.getAddress());
                }
                if (employee.getOrganizationId() != null) {
                    existingEmployee.setOrganizationId(employee.getOrganizationId());
                }
                if (employee.getOrganization() != null) {
                    existingEmployee.setOrganization(employee.getOrganization());
                }
                if (employee.getNationalIdentifier() != null) {
                    existingEmployee.setNationalIdentifier(employee.getNationalIdentifier());
                }
                if (employee.getUid() != null) {
                    existingEmployee.setUid(employee.getUid());
                }
                if (employee.getParentDepartmentId() != null) {
                    existingEmployee.setParentDepartmentId(employee.getParentDepartmentId());
                }
                if (employee.getSlmMilitaryFlag() != null) {
                    existingEmployee.setSlmMilitaryFlag(employee.getSlmMilitaryFlag());
                }
                if (employee.getMilitaryFirstName() != null) {
                    existingEmployee.setMilitaryFirstName(employee.getMilitaryFirstName());
                }
                if (employee.getSlmName() != null) {
                    existingEmployee.setSlmName(employee.getSlmName());
                }
                if (employee.getAge() != null) {
                    existingEmployee.setAge(employee.getAge());
                }
                if (employee.getDateOfHire() != null) {
                    existingEmployee.setDateOfHire(employee.getDateOfHire());
                }
                if (employee.getDateOfLastHire() != null) {
                    existingEmployee.setDateOfLastHire(employee.getDateOfLastHire());
                }
                if (employee.getGender() != null) {
                    existingEmployee.setGender(employee.getGender());
                }
                if (employee.getJobId() != null) {
                    existingEmployee.setJobId(employee.getJobId());
                }
                if (employee.getNationalityCode() != null) {
                    existingEmployee.setNationalityCode(employee.getNationalityCode());
                }
                if (employee.getNationality() != null) {
                    existingEmployee.setNationality(employee.getNationality());
                }
                if (employee.getPersonStartDate() != null) {
                    existingEmployee.setPersonStartDate(employee.getPersonStartDate());
                }
                if (employee.getOriginalDateOfHire() != null) {
                    existingEmployee.setOriginalDateOfHire(employee.getOriginalDateOfHire());
                }
                if (employee.getSupervisorId() != null) {
                    existingEmployee.setSupervisorId(employee.getSupervisorId());
                }
                if (employee.getSupervisorFullName() != null) {
                    existingEmployee.setSupervisorFullName(employee.getSupervisorFullName());
                }
                if (employee.getParentDepartmentName() != null) {
                    existingEmployee.setParentDepartmentName(employee.getParentDepartmentName());
                }
                if (employee.getSectionId() != null) {
                    existingEmployee.setSectionId(employee.getSectionId());
                }
                if (employee.getSectionName() != null) {
                    existingEmployee.setSectionName(employee.getSectionName());
                }
                if (employee.getAdjustedServiceDate() != null) {
                    existingEmployee.setAdjustedServiceDate(employee.getAdjustedServiceDate());
                }
                if (employee.getQualificationType() != null) {
                    existingEmployee.setQualificationType(employee.getQualificationType());
                }
                if (employee.getQualificationSpecification() != null) {
                    existingEmployee.setQualificationSpecification(employee.getQualificationSpecification());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        LOG.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
