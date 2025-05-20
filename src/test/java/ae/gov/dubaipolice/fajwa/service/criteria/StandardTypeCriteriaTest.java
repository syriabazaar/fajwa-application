package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StandardTypeCriteriaTest {

    @Test
    void newStandardTypeCriteriaHasAllFiltersNullTest() {
        var standardTypeCriteria = new StandardTypeCriteria();
        assertThat(standardTypeCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void standardTypeCriteriaFluentMethodsCreatesFiltersTest() {
        var standardTypeCriteria = new StandardTypeCriteria();

        setAllFilters(standardTypeCriteria);

        assertThat(standardTypeCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void standardTypeCriteriaCopyCreatesNullFilterTest() {
        var standardTypeCriteria = new StandardTypeCriteria();
        var copy = standardTypeCriteria.copy();

        assertThat(standardTypeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(standardTypeCriteria)
        );
    }

    @Test
    void standardTypeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var standardTypeCriteria = new StandardTypeCriteria();
        setAllFilters(standardTypeCriteria);

        var copy = standardTypeCriteria.copy();

        assertThat(standardTypeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(standardTypeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var standardTypeCriteria = new StandardTypeCriteria();

        assertThat(standardTypeCriteria).hasToString("StandardTypeCriteria{}");
    }

    private static void setAllFilters(StandardTypeCriteria standardTypeCriteria) {
        standardTypeCriteria.id();
        standardTypeCriteria.name();
        standardTypeCriteria.jobDescId();
        standardTypeCriteria.distinct();
    }

    private static Condition<StandardTypeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getJobDescId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StandardTypeCriteria> copyFiltersAre(
        StandardTypeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getJobDescId(), copy.getJobDescId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
