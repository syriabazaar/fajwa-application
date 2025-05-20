package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.Nomination} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.NominationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nominations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NominationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter machPerc;

    private LongFilter employeeId;

    private LongFilter jobVacantId;

    private LongFilter appointmentId;

    private Boolean distinct;

    public NominationCriteria() {}

    public NominationCriteria(NominationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.machPerc = other.optionalMachPerc().map(FloatFilter::copy).orElse(null);
        this.employeeId = other.optionalEmployeeId().map(LongFilter::copy).orElse(null);
        this.jobVacantId = other.optionalJobVacantId().map(LongFilter::copy).orElse(null);
        this.appointmentId = other.optionalAppointmentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public NominationCriteria copy() {
        return new NominationCriteria(this);
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

    public FloatFilter getMachPerc() {
        return machPerc;
    }

    public Optional<FloatFilter> optionalMachPerc() {
        return Optional.ofNullable(machPerc);
    }

    public FloatFilter machPerc() {
        if (machPerc == null) {
            setMachPerc(new FloatFilter());
        }
        return machPerc;
    }

    public void setMachPerc(FloatFilter machPerc) {
        this.machPerc = machPerc;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public Optional<LongFilter> optionalEmployeeId() {
        return Optional.ofNullable(employeeId);
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            setEmployeeId(new LongFilter());
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getJobVacantId() {
        return jobVacantId;
    }

    public Optional<LongFilter> optionalJobVacantId() {
        return Optional.ofNullable(jobVacantId);
    }

    public LongFilter jobVacantId() {
        if (jobVacantId == null) {
            setJobVacantId(new LongFilter());
        }
        return jobVacantId;
    }

    public void setJobVacantId(LongFilter jobVacantId) {
        this.jobVacantId = jobVacantId;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public Optional<LongFilter> optionalAppointmentId() {
        return Optional.ofNullable(appointmentId);
    }

    public LongFilter appointmentId() {
        if (appointmentId == null) {
            setAppointmentId(new LongFilter());
        }
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
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
        final NominationCriteria that = (NominationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(machPerc, that.machPerc) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(jobVacantId, that.jobVacantId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, machPerc, employeeId, jobVacantId, appointmentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NominationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMachPerc().map(f -> "machPerc=" + f + ", ").orElse("") +
            optionalEmployeeId().map(f -> "employeeId=" + f + ", ").orElse("") +
            optionalJobVacantId().map(f -> "jobVacantId=" + f + ", ").orElse("") +
            optionalAppointmentId().map(f -> "appointmentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
