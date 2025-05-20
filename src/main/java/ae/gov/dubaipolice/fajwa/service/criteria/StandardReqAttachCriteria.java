package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.StandardReqAttach} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.StandardReqAttachResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /standard-req-attaches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandardReqAttachCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter attDesc;

    private Boolean distinct;

    public StandardReqAttachCriteria() {}

    public StandardReqAttachCriteria(StandardReqAttachCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.attDesc = other.optionalAttDesc().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StandardReqAttachCriteria copy() {
        return new StandardReqAttachCriteria(this);
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

    public StringFilter getAttDesc() {
        return attDesc;
    }

    public Optional<StringFilter> optionalAttDesc() {
        return Optional.ofNullable(attDesc);
    }

    public StringFilter attDesc() {
        if (attDesc == null) {
            setAttDesc(new StringFilter());
        }
        return attDesc;
    }

    public void setAttDesc(StringFilter attDesc) {
        this.attDesc = attDesc;
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
        final StandardReqAttachCriteria that = (StandardReqAttachCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(attDesc, that.attDesc) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attDesc, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandardReqAttachCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalAttDesc().map(f -> "attDesc=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
