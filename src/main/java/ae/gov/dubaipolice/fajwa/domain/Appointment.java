package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "app_date_time")
    private Instant appDateTime;

    @Column(name = "interveiew_date")
    private Instant interveiewDate;

    @JsonIgnoreProperties(value = { "employee", "jobVacant", "appointment" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Nomination nomination;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appointment")
    @JsonIgnoreProperties(value = { "standarditem", "appointment" }, allowSetters = true)
    private Set<IntrvuStrdDtls> intrvuStrdDtls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Appointment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAppDateTime() {
        return this.appDateTime;
    }

    public Appointment appDateTime(Instant appDateTime) {
        this.setAppDateTime(appDateTime);
        return this;
    }

    public void setAppDateTime(Instant appDateTime) {
        this.appDateTime = appDateTime;
    }

    public Instant getInterveiewDate() {
        return this.interveiewDate;
    }

    public Appointment interveiewDate(Instant interveiewDate) {
        this.setInterveiewDate(interveiewDate);
        return this;
    }

    public void setInterveiewDate(Instant interveiewDate) {
        this.interveiewDate = interveiewDate;
    }

    public Nomination getNomination() {
        return this.nomination;
    }

    public void setNomination(Nomination nomination) {
        this.nomination = nomination;
    }

    public Appointment nomination(Nomination nomination) {
        this.setNomination(nomination);
        return this;
    }

    public Set<IntrvuStrdDtls> getIntrvuStrdDtls() {
        return this.intrvuStrdDtls;
    }

    public void setIntrvuStrdDtls(Set<IntrvuStrdDtls> intrvuStrdDtls) {
        if (this.intrvuStrdDtls != null) {
            this.intrvuStrdDtls.forEach(i -> i.setAppointment(null));
        }
        if (intrvuStrdDtls != null) {
            intrvuStrdDtls.forEach(i -> i.setAppointment(this));
        }
        this.intrvuStrdDtls = intrvuStrdDtls;
    }

    public Appointment intrvuStrdDtls(Set<IntrvuStrdDtls> intrvuStrdDtls) {
        this.setIntrvuStrdDtls(intrvuStrdDtls);
        return this;
    }

    public Appointment addIntrvuStrdDtls(IntrvuStrdDtls intrvuStrdDtls) {
        this.intrvuStrdDtls.add(intrvuStrdDtls);
        intrvuStrdDtls.setAppointment(this);
        return this;
    }

    public Appointment removeIntrvuStrdDtls(IntrvuStrdDtls intrvuStrdDtls) {
        this.intrvuStrdDtls.remove(intrvuStrdDtls);
        intrvuStrdDtls.setAppointment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return getId() != null && getId().equals(((Appointment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", appDateTime='" + getAppDateTime() + "'" +
            ", interveiewDate='" + getInterveiewDate() + "'" +
            "}";
    }
}
