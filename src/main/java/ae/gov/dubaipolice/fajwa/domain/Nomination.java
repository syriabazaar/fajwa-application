package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Nomination.
 */
@Entity
@Table(name = "nomination")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nomination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mach_perc")
    private Float machPerc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "intrvuReqAttches" }, allowSetters = true)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "department", "jobDesc", "nominations" }, allowSetters = true)
    private JobVacant jobVacant;

    @JsonIgnoreProperties(value = { "nomination", "intrvuStrdDtls" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nomination")
    private Appointment appointment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nomination id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMachPerc() {
        return this.machPerc;
    }

    public Nomination machPerc(Float machPerc) {
        this.setMachPerc(machPerc);
        return this;
    }

    public void setMachPerc(Float machPerc) {
        this.machPerc = machPerc;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Nomination employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public JobVacant getJobVacant() {
        return this.jobVacant;
    }

    public void setJobVacant(JobVacant jobVacant) {
        this.jobVacant = jobVacant;
    }

    public Nomination jobVacant(JobVacant jobVacant) {
        this.setJobVacant(jobVacant);
        return this;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        if (this.appointment != null) {
            this.appointment.setNomination(null);
        }
        if (appointment != null) {
            appointment.setNomination(this);
        }
        this.appointment = appointment;
    }

    public Nomination appointment(Appointment appointment) {
        this.setAppointment(appointment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nomination)) {
            return false;
        }
        return getId() != null && getId().equals(((Nomination) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nomination{" +
            "id=" + getId() +
            ", machPerc=" + getMachPerc() +
            "}";
    }
}
