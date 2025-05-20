package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StandarditemCriteriaTest {

    @Test
    void newStandarditemCriteriaHasAllFiltersNullTest() {
        var standarditemCriteria = new StandarditemCriteria();
        assertThat(standarditemCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void standarditemCriteriaFluentMethodsCreatesFiltersTest() {
        var standarditemCriteria = new StandarditemCriteria();

        setAllFilters(standarditemCriteria);

        assertThat(standarditemCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void standarditemCriteriaCopyCreatesNullFilterTest() {
        var standarditemCriteria = new StandarditemCriteria();
        var copy = standarditemCriteria.copy();

        assertThat(standarditemCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(standarditemCriteria)
        );
    }

    @Test
    void standarditemCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var standarditemCriteria = new StandarditemCriteria();
        setAllFilters(standarditemCriteria);

        var copy = standarditemCriteria.copy();

        assertThat(standarditemCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(standarditemCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var standarditemCriteria = new StandarditemCriteria();

        assertThat(standarditemCriteria).hasToString("StandarditemCriteria{}");
    }

    private static void setAllFilters(StandarditemCriteria standarditemCriteria) {
        standarditemCriteria.id();
        standarditemCriteria.name();
        standarditemCriteria.isActive();
        standarditemCriteria.weightPercentage();
        standarditemCriteria.standardCatId();
        standarditemCriteria.intrvuStrdDtlsId();
        standarditemCriteria.distinct();
    }

    private static Condition<StandarditemCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getIsActive()) &&
                condition.apply(criteria.getWeightPercentage()) &&
                condition.apply(criteria.getStandardCatId()) &&
                condition.apply(criteria.getIntrvuStrdDtlsId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StandarditemCriteria> copyFiltersAre(
        StandarditemCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getIsActive(), copy.getIsActive()) &&
                condition.apply(criteria.getWeightPercentage(), copy.getWeightPercentage()) &&
                condition.apply(criteria.getStandardCatId(), copy.getStandardCatId()) &&
                condition.apply(criteria.getIntrvuStrdDtlsId(), copy.getIntrvuStrdDtlsId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
