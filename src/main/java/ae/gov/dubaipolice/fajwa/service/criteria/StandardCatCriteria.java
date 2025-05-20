package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.StandardCat} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.StandardCatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /standard-cats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandardCatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter standardParentId;

    private LongFilter reqAttachmentId;

    private Boolean distinct;

    public StandardCatCriteria() {}

    public StandardCatCriteria(StandardCatCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.standardParentId = other.optionalStandardParentId().map(LongFilter::copy).orElse(null);
        this.reqAttachmentId = other.optionalReqAttachmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StandardCatCriteria copy() {
        return new StandardCatCriteria(this);
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

    public LongFilter getStandardParentId() {
        return standardParentId;
    }

    public Optional<LongFilter> optionalStandardParentId() {
        return Optional.ofNullable(standardParentId);
    }

    public LongFilter standardParentId() {
        if (standardParentId == null) {
            setStandardParentId(new LongFilter());
        }
        return standardParentId;
    }

    public void setStandardParentId(LongFilter standardParentId) {
        this.standardParentId = standardParentId;
    }

    public LongFilter getReqAttachmentId() {
        return reqAttachmentId;
    }

    public Optional<LongFilter> optionalReqAttachmentId() {
        return Optional.ofNullable(reqAttachmentId);
    }

    public LongFilter reqAttachmentId() {
        if (reqAttachmentId == null) {
            setReqAttachmentId(new LongFilter());
        }
        return reqAttachmentId;
    }

    public void setReqAttachmentId(LongFilter reqAttachmentId) {
        this.reqAttachmentId = reqAttachmentId;
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
        final StandardCatCriteria that = (StandardCatCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(standardParentId, that.standardParentId) &&
            Objects.equals(reqAttachmentId, that.reqAttachmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, standardParentId, reqAttachmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandardCatCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalStandardParentId().map(f -> "standardParentId=" + f + ", ").orElse("") +
            optionalReqAttachmentId().map(f -> "reqAttachmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
