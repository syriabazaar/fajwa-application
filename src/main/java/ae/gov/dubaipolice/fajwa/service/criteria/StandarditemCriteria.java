package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.Standarditem} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.StandarditemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /standarditems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandarditemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isActive;

    private FloatFilter weightPercentage;

    private LongFilter standardCatId;

    private LongFilter intrvuStrdDtlsId;

    private Boolean distinct;

    public StandarditemCriteria() {}

    public StandarditemCriteria(StandarditemCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.weightPercentage = other.optionalWeightPercentage().map(FloatFilter::copy).orElse(null);
        this.standardCatId = other.optionalStandardCatId().map(LongFilter::copy).orElse(null);
        this.intrvuStrdDtlsId = other.optionalIntrvuStrdDtlsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StandarditemCriteria copy() {
        return new StandarditemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public FloatFilter getWeightPercentage() {
        return weightPercentage;
    }

    public Optional<FloatFilter> optionalWeightPercentage() {
        return Optional.ofNullable(weightPercentage);
    }

    public FloatFilter weightPercentage() {
        if (weightPercentage == null) {
            setWeightPercentage(new FloatFilter());
        }
        return weightPercentage;
    }

    public void setWeightPercentage(FloatFilter weightPercentage) {
        this.weightPercentage = weightPercentage;
    }

    public LongFilter getStandardCatId() {
        return standardCatId;
    }

    public Optional<LongFilter> optionalStandardCatId() {
        return Optional.ofNullable(standardCatId);
    }

    public LongFilter standardCatId() {
        if (standardCatId == null) {
            setStandardCatId(new LongFilter());
        }
        return standardCatId;
    }

    public void setStandardCatId(LongFilter standardCatId) {
        this.standardCatId = standardCatId;
    }

    public LongFilter getIntrvuStrdDtlsId() {
        return intrvuStrdDtlsId;
    }

    public Optional<LongFilter> optionalIntrvuStrdDtlsId() {
        return Optional.ofNullable(intrvuStrdDtlsId);
    }

    public LongFilter intrvuStrdDtlsId() {
        if (intrvuStrdDtlsId == null) {
            setIntrvuStrdDtlsId(new LongFilter());
        }
        return intrvuStrdDtlsId;
    }

    public void setIntrvuStrdDtlsId(LongFilter intrvuStrdDtlsId) {
        this.intrvuStrdDtlsId = intrvuStrdDtlsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StandarditemCriteria that = (StandarditemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(weightPercentage, that.weightPercentage) &&
            Objects.equals(standardCatId, that.standardCatId) &&
            Objects.equals(intrvuStrdDtlsId, that.intrvuStrdDtlsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive, weightPercentage, standardCatId, intrvuStrdDtlsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandarditemCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalWeightPercentage().map(f -> "weightPercentage=" + f + ", ").orElse("") +
            optionalStandardCatId().map(f -> "standardCatId=" + f + ", ").orElse("") +
            optionalIntrvuStrdDtlsId().map(f -> "intrvuStrdDtlsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
