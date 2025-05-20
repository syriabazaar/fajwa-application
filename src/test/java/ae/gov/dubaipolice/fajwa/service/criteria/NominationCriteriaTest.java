package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class NominationCriteriaTest {

    @Test
    void newNominationCriteriaHasAllFiltersNullTest() {
        var nominationCriteria = new NominationCriteria();
        assertThat(nominationCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void nominationCriteriaFluentMethodsCreatesFiltersTest() {
        var nominationCriteria = new NominationCriteria();

        setAllFilters(nominationCriteria);

        assertThat(nominationCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void nominationCriteriaCopyCreatesNullFilterTest() {
        var nominationCriteria = new NominationCriteria();
        var copy = nominationCriteria.copy();

        assertThat(nominationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(nominationCriteria)
        );
    }

    @Test
    void nominationCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var nominationCriteria = new NominationCriteria();
        setAllFilters(nominationCriteria);

        var copy = nominationCriteria.copy();

        assertThat(nominationCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(nominationCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var nominationCriteria = new NominationCriteria();

        assertThat(nominationCriteria).hasToString("NominationCriteria{}");
    }

    private static void setAllFilters(NominationCriteria nominationCriteria) {
        nominationCriteria.id();
        nominationCriteria.machPerc();
        nominationCriteria.employeeId();
        nominationCriteria.jobVacantId();
        nominationCriteria.appointmentId();
        nominationCriteria.distinct();
    }

    private static Condition<NominationCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getMachPerc()) &&
                condition.apply(criteria.getEmployeeId()) &&
                condition.apply(criteria.getJobVacantId()) &&
                condition.apply(criteria.getAppointmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<NominationCriteria> copyFiltersAre(NominationCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getMachPerc(), copy.getMachPerc()) &&
                condition.apply(criteria.getEmployeeId(), copy.getEmployeeId()) &&
                condition.apply(criteria.getJobVacantId(), copy.getJobVacantId()) &&
                condition.apply(criteria.getAppointmentId(), copy.getAppointmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
