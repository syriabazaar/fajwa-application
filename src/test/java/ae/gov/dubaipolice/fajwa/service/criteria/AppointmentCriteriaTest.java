package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AppointmentCriteriaTest {

    @Test
    void newAppointmentCriteriaHasAllFiltersNullTest() {
        var appointmentCriteria = new AppointmentCriteria();
        assertThat(appointmentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void appointmentCriteriaFluentMethodsCreatesFiltersTest() {
        var appointmentCriteria = new AppointmentCriteria();

        setAllFilters(appointmentCriteria);

        assertThat(appointmentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void appointmentCriteriaCopyCreatesNullFilterTest() {
        var appointmentCriteria = new AppointmentCriteria();
        var copy = appointmentCriteria.copy();

        assertThat(appointmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(appointmentCriteria)
        );
    }

    @Test
    void appointmentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var appointmentCriteria = new AppointmentCriteria();
        setAllFilters(appointmentCriteria);

        var copy = appointmentCriteria.copy();

        assertThat(appointmentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(appointmentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var appointmentCriteria = new AppointmentCriteria();

        assertThat(appointmentCriteria).hasToString("AppointmentCriteria{}");
    }

    private static void setAllFilters(AppointmentCriteria appointmentCriteria) {
        appointmentCriteria.id();
        appointmentCriteria.appDateTime();
        appointmentCriteria.interveiewDate();
        appointmentCriteria.nominationId();
        appointmentCriteria.intrvuStrdDtlsId();
        appointmentCriteria.distinct();
    }

    private static Condition<AppointmentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getAppDateTime()) &&
                condition.apply(criteria.getInterveiewDate()) &&
                condition.apply(criteria.getNominationId()) &&
                condition.apply(criteria.getIntrvuStrdDtlsId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AppointmentCriteria> copyFiltersAre(AppointmentCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getAppDateTime(), copy.getAppDateTime()) &&
                condition.apply(criteria.getInterveiewDate(), copy.getInterveiewDate()) &&
                condition.apply(criteria.getNominationId(), copy.getNominationId()) &&
                condition.apply(criteria.getIntrvuStrdDtlsId(), copy.getIntrvuStrdDtlsId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
