package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StandardParentCriteriaTest {

    @Test
    void newStandardParentCriteriaHasAllFiltersNullTest() {
        var standardParentCriteria = new StandardParentCriteria();
        assertThat(standardParentCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void standardParentCriteriaFluentMethodsCreatesFiltersTest() {
        var standardParentCriteria = new StandardParentCriteria();

        setAllFilters(standardParentCriteria);

        assertThat(standardParentCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void standardParentCriteriaCopyCreatesNullFilterTest() {
        var standardParentCriteria = new StandardParentCriteria();
        var copy = standardParentCriteria.copy();

        assertThat(standardParentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(standardParentCriteria)
        );
    }

    @Test
    void standardParentCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var standardParentCriteria = new StandardParentCriteria();
        setAllFilters(standardParentCriteria);

        var copy = standardParentCriteria.copy();

        assertThat(standardParentCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(standardParentCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var standardParentCriteria = new StandardParentCriteria();

        assertThat(standardParentCriteria).hasToString("StandardParentCriteria{}");
    }

    private static void setAllFilters(StandardParentCriteria standardParentCriteria) {
        standardParentCriteria.id();
        standardParentCriteria.name();
        standardParentCriteria.standardTypeId();
        standardParentCriteria.distinct();
    }

    private static Condition<StandardParentCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getStandardTypeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StandardParentCriteria> copyFiltersAre(
        StandardParentCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getStandardTypeId(), copy.getStandardTypeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
