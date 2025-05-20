package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StandardReqAttachCriteriaTest {

    @Test
    void newStandardReqAttachCriteriaHasAllFiltersNullTest() {
        var standardReqAttachCriteria = new StandardReqAttachCriteria();
        assertThat(standardReqAttachCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void standardReqAttachCriteriaFluentMethodsCreatesFiltersTest() {
        var standardReqAttachCriteria = new StandardReqAttachCriteria();

        setAllFilters(standardReqAttachCriteria);

        assertThat(standardReqAttachCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void standardReqAttachCriteriaCopyCreatesNullFilterTest() {
        var standardReqAttachCriteria = new StandardReqAttachCriteria();
        var copy = standardReqAttachCriteria.copy();

        assertThat(standardReqAttachCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(standardReqAttachCriteria)
        );
    }

    @Test
    void standardReqAttachCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var standardReqAttachCriteria = new StandardReqAttachCriteria();
        setAllFilters(standardReqAttachCriteria);

        var copy = standardReqAttachCriteria.copy();

        assertThat(standardReqAttachCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(standardReqAttachCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var standardReqAttachCriteria = new StandardReqAttachCriteria();

        assertThat(standardReqAttachCriteria).hasToString("StandardReqAttachCriteria{}");
    }

    private static void setAllFilters(StandardReqAttachCriteria standardReqAttachCriteria) {
        standardReqAttachCriteria.id();
        standardReqAttachCriteria.name();
        standardReqAttachCriteria.attDesc();
        standardReqAttachCriteria.distinct();
    }

    private static Condition<StandardReqAttachCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getAttDesc()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StandardReqAttachCriteria> copyFiltersAre(
        StandardReqAttachCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getAttDesc(), copy.getAttDesc()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
