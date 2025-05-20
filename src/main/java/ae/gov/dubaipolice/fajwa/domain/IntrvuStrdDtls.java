package ae.gov.dubaipolice.fajwa.domain;

import ae.gov.dubaipolice.fajwa.domain.enumeration.StandardOption;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A IntrvuStrdDtls.
 */
@Entity
@Table(name = "intrvu_strd_dtls")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntrvuStrdDtls implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "standard_option")
    private StandardOption standardOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "standardCat", "intrvuStrdDtls" }, allowSetters = true)
    private Standarditem standarditem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "nomination", "intrvuStrdDtls" }, allowSetters = true)
    private Appointment appointment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntrvuStrdDtls id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StandardOption getStandardOption() {
        return this.standardOption;
    }

    public IntrvuStrdDtls standardOption(StandardOption standardOption) {
        this.setStandardOption(standardOption);
        return this;
    }

    public void setStandardOption(StandardOption standardOption) {
        this.standardOption = standardOption;
    }

    public Standarditem getStandarditem() {
        return this.standarditem;
    }

    public void setStandarditem(Standarditem standarditem) {
        this.standarditem = standarditem;
    }

    public IntrvuStrdDtls standarditem(Standarditem standarditem) {
        this.setStandarditem(standarditem);
        return this;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public IntrvuStrdDtls appointment(Appointment appointment) {
        this.setAppointment(appointment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntrvuStrdDtls)) {
            return false;
        }
        return getId() != null && getId().equals(((IntrvuStrdDtls) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntrvuStrdDtls{" +
            "id=" + getId() +
            ", standardOption='" + getStandardOption() + "'" +
            "}";
    }
}
