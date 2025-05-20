package ae.gov.dubaipolice.fajwa.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class StandardCatCriteriaTest {

    @Test
    void newStandardCatCriteriaHasAllFiltersNullTest() {
        var standardCatCriteria = new StandardCatCriteria();
        assertThat(standardCatCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void standardCatCriteriaFluentMethodsCreatesFiltersTest() {
        var standardCatCriteria = new StandardCatCriteria();

        setAllFilters(standardCatCriteria);

        assertThat(standardCatCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void standardCatCriteriaCopyCreatesNullFilterTest() {
        var standardCatCriteria = new StandardCatCriteria();
        var copy = standardCatCriteria.copy();

        assertThat(standardCatCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(standardCatCriteria)
        );
    }

    @Test
    void standardCatCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var standardCatCriteria = new StandardCatCriteria();
        setAllFilters(standardCatCriteria);

        var copy = standardCatCriteria.copy();

        assertThat(standardCatCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(standardCatCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var standardCatCriteria = new StandardCatCriteria();

        assertThat(standardCatCriteria).hasToString("StandardCatCriteria{}");
    }

    private static void setAllFilters(StandardCatCriteria standardCatCriteria) {
        standardCatCriteria.id();
        standardCatCriteria.name();
        standardCatCriteria.standardParentId();
        standardCatCriteria.reqAttachmentId();
        standardCatCriteria.distinct();
    }

    private static Condition<StandardCatCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getStandardParentId()) &&
                condition.apply(criteria.getReqAttachmentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<StandardCatCriteria> copyFiltersAre(StandardCatCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getStandardParentId(), copy.getStandardParentId()) &&
                condition.apply(criteria.getReqAttachmentId(), copy.getReqAttachmentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
