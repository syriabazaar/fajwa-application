package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmployeeCriteriaTest {

    @Test
    void newEmployeeCriteriaHasAllFiltersNullTest() {
        var employeeCriteria = new EmployeeCriteria();
        assertThat(employeeCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void employeeCriteriaFluentMethodsCreatesFiltersTest() {
        var employeeCriteria = new EmployeeCriteria();

        setAllFilters(employeeCriteria);

        assertThat(employeeCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void employeeCriteriaCopyCreatesNullFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void employeeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var employeeCriteria = new EmployeeCriteria();
        setAllFilters(employeeCriteria);

        var copy = employeeCriteria.copy();

        assertThat(employeeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(employeeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var employeeCriteria = new EmployeeCriteria();

        assertThat(employeeCriteria).hasToString("EmployeeCriteria{}");
    }

    private static void setAllFilters(EmployeeCriteria employeeCriteria) {
        employeeCriteria.id();
        employeeCriteria.fullname();
        employeeCriteria.gradeId();
        employeeCriteria.gradeName();
        employeeCriteria.homePhone();
        employeeCriteria.mobileNumber();
        employeeCriteria.assigmentId();
        employeeCriteria.dateOfAssignment();
        employeeCriteria.jobName();
        employeeCriteria.address();
        employeeCriteria.organizationId();
        employeeCriteria.organization();
        employeeCriteria.nationalIdentifier();
        employeeCriteria.uid();
        employeeCriteria.parentDepartmentId();
        employeeCriteria.slmMilitaryFlag();
        employeeCriteria.militaryFirstName();
        employeeCriteria.slmName();
        employeeCriteria.age();
        employeeCriteria.dateOfHire();
        employeeCriteria.dateOfLastHire();
        employeeCriteria.gender();
        employeeCriteria.jobId();
        employeeCriteria.nationalityCode();
        employeeCriteria.nationality();
        employeeCriteria.personStartDate();
        employeeCriteria.originalDateOfHire();
        employeeCriteria.supervisorId();
        employeeCriteria.supervisorFullName();
        employeeCriteria.parentDepartmentName();
        employeeCriteria.sectionId();
        employeeCriteria.sectionName();
        employeeCriteria.adjustedServiceDate();
        employeeCriteria.qualificationType();
        employeeCriteria.qualificationSpecification();
        employeeCriteria.intrvuReqAttchId();
        employeeCriteria.distinct();
    }

    private static Condition<EmployeeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getFullname()) &&
                condition.apply(criteria.getGradeId()) &&
                condition.apply(criteria.getGradeName()) &&
                condition.apply(criteria.getHomePhone()) &&
                condition.apply(criteria.getMobileNumber()) &&
                condition.apply(criteria.getAssigmentId()) &&
                condition.apply(criteria.getDateOfAssignment()) &&
                condition.apply(criteria.getJobName()) &&
                condition.apply(criteria.getAddress()) &&
                condition.apply(criteria.getOrganizationId()) &&
                condition.apply(criteria.getOrganization()) &&
                condition.apply(criteria.getNationalIdentifier()) &&
                condition.apply(criteria.getUid()) &&
                condition.apply(criteria.getParentDepartmentId()) &&
                condition.apply(criteria.getSlmMilitaryFlag()) &&
                condition.apply(criteria.getMilitaryFirstName()) &&
                condition.apply(criteria.getSlmName()) &&
                condition.apply(criteria.getAge()) &&
                condition.apply(criteria.getDateOfHire()) &&
                condition.apply(criteria.getDateOfLastHire()) &&
                condition.apply(criteria.getGender()) &&
                condition.apply(criteria.getJobId()) &&
                condition.apply(criteria.getNationalityCode()) &&
                condition.apply(criteria.getNationality()) &&
                condition.apply(criteria.getPersonStartDate()) &&
                condition.apply(criteria.getOriginalDateOfHire()) &&
                condition.apply(criteria.getSupervisorId()) &&
                condition.apply(criteria.getSupervisorFullName()) &&
                condition.apply(criteria.getParentDepartmentName()) &&
                condition.apply(criteria.getSectionId()) &&
                condition.apply(criteria.getSectionName()) &&
                condition.apply(criteria.getAdjustedServiceDate()) &&
                condition.apply(criteria.getQualificationType()) &&
                condition.apply(criteria.getQualificationSpecification()) &&
                condition.apply(criteria.getIntrvuReqAttchId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmployeeCriteria> copyFiltersAre(EmployeeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getFullname(), copy.getFullname()) &&
                condition.apply(criteria.getGradeId(), copy.getGradeId()) &&
                condition.apply(criteria.getGradeName(), copy.getGradeName()) &&
                condition.apply(criteria.getHomePhone(), copy.getHomePhone()) &&
                condition.apply(criteria.getMobileNumber(), copy.getMobileNumber()) &&
                condition.apply(criteria.getAssigmentId(), copy.getAssigmentId()) &&
                condition.apply(criteria.getDateOfAssignment(), copy.getDateOfAssignment()) &&
                condition.apply(criteria.getJobName(), copy.getJobName()) &&
                condition.apply(criteria.getAddress(), copy.getAddress()) &&
                condition.apply(criteria.getOrganizationId(), copy.getOrganizationId()) &&
                condition.apply(criteria.getOrganization(), copy.getOrganization()) &&
                condition.apply(criteria.getNationalIdentifier(), copy.getNationalIdentifier()) &&
                condition.apply(criteria.getUid(), copy.getUid()) &&
                condition.apply(criteria.getParentDepartmentId(), copy.getParentDepartmentId()) &&
                condition.apply(criteria.getSlmMilitaryFlag(), copy.getSlmMilitaryFlag()) &&
                condition.apply(criteria.getMilitaryFirstName(), copy.getMilitaryFirstName()) &&
                condition.apply(criteria.getSlmName(), copy.getSlmName()) &&
                condition.apply(criteria.getAge(), copy.getAge()) &&
                condition.apply(criteria.getDateOfHire(), copy.getDateOfHire()) &&
                condition.apply(criteria.getDateOfLastHire(), copy.getDateOfLastHire()) &&
                condition.apply(criteria.getGender(), copy.getGender()) &&
                condition.apply(criteria.getJobId(), copy.getJobId()) &&
                condition.apply(criteria.getNationalityCode(), copy.getNationalityCode()) &&
                condition.apply(criteria.getNationality(), copy.getNationality()) &&
                condition.apply(criteria.getPersonStartDate(), copy.getPersonStartDate()) &&
                condition.apply(criteria.getOriginalDateOfHire(), copy.getOriginalDateOfHire()) &&
                condition.apply(criteria.getSupervisorId(), copy.getSupervisorId()) &&
                condition.apply(criteria.getSupervisorFullName(), copy.getSupervisorFullName()) &&
                condition.apply(criteria.getParentDepartmentName(), copy.getParentDepartmentName()) &&
                condition.apply(criteria.getSectionId(), copy.getSectionId()) &&
                condition.apply(criteria.getSectionName(), copy.getSectionName()) &&
                condition.apply(criteria.getAdjustedServiceDate(), copy.getAdjustedServiceDate()) &&
                condition.apply(criteria.getQualificationType(), copy.getQualificationType()) &&
                condition.apply(criteria.getQualificationSpecification(), copy.getQualificationSpecification()) &&
                condition.apply(criteria.getIntrvuReqAttchId(), copy.getIntrvuReqAttchId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
