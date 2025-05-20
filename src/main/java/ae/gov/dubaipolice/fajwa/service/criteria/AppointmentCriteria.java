package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.Appointment} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.AppointmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /appointments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppointmentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter appDateTime;

    private InstantFilter interveiewDate;

    private LongFilter nominationId;

    private LongFilter intrvuStrdDtlsId;

    private Boolean distinct;

    public AppointmentCriteria() {}

    public AppointmentCriteria(AppointmentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.appDateTime = other.optionalAppDateTime().map(InstantFilter::copy).orElse(null);
        this.interveiewDate = other.optionalInterveiewDate().map(InstantFilter::copy).orElse(null);
        this.nominationId = other.optionalNominationId().map(LongFilter::copy).orElse(null);
        this.intrvuStrdDtlsId = other.optionalIntrvuStrdDtlsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AppointmentCriteria copy() {
        return new AppointmentCriteria(this);
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

    public InstantFilter getAppDateTime() {
        return appDateTime;
    }

    public Optional<InstantFilter> optionalAppDateTime() {
        return Optional.ofNullable(appDateTime);
    }

    public InstantFilter appDateTime() {
        if (appDateTime == null) {
            setAppDateTime(new InstantFilter());
        }
        return appDateTime;
    }

    public void setAppDateTime(InstantFilter appDateTime) {
        this.appDateTime = appDateTime;
    }

    public InstantFilter getInterveiewDate() {
        return interveiewDate;
    }

    public Optional<InstantFilter> optionalInterveiewDate() {
        return Optional.ofNullable(interveiewDate);
    }

    public InstantFilter interveiewDate() {
        if (interveiewDate == null) {
            setInterveiewDate(new InstantFilter());
        }
        return interveiewDate;
    }

    public void setInterveiewDate(InstantFilter interveiewDate) {
        this.interveiewDate = interveiewDate;
    }

    public LongFilter getNominationId() {
        return nominationId;
    }

    public Optional<LongFilter> optionalNominationId() {
        return Optional.ofNullable(nominationId);
    }

    public LongFilter nominationId() {
        if (nominationId == null) {
            setNominationId(new LongFilter());
        }
        return nominationId;
    }

    public void setNominationId(LongFilter nominationId) {
        this.nominationId = nominationId;
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
        final AppointmentCriteria that = (AppointmentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(appDateTime, that.appDateTime) &&
            Objects.equals(interveiewDate, that.interveiewDate) &&
            Objects.equals(nominationId, that.nominationId) &&
            Objects.equals(intrvuStrdDtlsId, that.intrvuStrdDtlsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appDateTime, interveiewDate, nominationId, intrvuStrdDtlsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppointmentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAppDateTime().map(f -> "appDateTime=" + f + ", ").orElse("") +
            optionalInterveiewDate().map(f -> "interveiewDate=" + f + ", ").orElse("") +
            optionalNominationId().map(f -> "nominationId=" + f + ", ").orElse("") +
            optionalIntrvuStrdDtlsId().map(f -> "intrvuStrdDtlsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
