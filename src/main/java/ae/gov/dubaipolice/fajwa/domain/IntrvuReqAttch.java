package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A IntrvuReqAttch.
 */
@Entity
@Table(name = "intrvu_req_attch")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IntrvuReqAttch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "add_date")
    private Instant addDate;

    @Lob
    @Column(name = "attch")
    private byte[] attch;

    @Column(name = "attch_content_type")
    private String attchContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private StandardReqAttach standardReqAttach;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "intrvuReqAttches" }, allowSetters = true)
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IntrvuReqAttch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAddDate() {
        return this.addDate;
    }

    public IntrvuReqAttch addDate(Instant addDate) {
        this.setAddDate(addDate);
        return this;
    }

    public void setAddDate(Instant addDate) {
        this.addDate = addDate;
    }

    public byte[] getAttch() {
        return this.attch;
    }

    public IntrvuReqAttch attch(byte[] attch) {
        this.setAttch(attch);
        return this;
    }

    public void setAttch(byte[] attch) {
        this.attch = attch;
    }

    public String getAttchContentType() {
        return this.attchContentType;
    }

    public IntrvuReqAttch attchContentType(String attchContentType) {
        this.attchContentType = attchContentType;
        return this;
    }

    public void setAttchContentType(String attchContentType) {
        this.attchContentType = attchContentType;
    }

    public StandardReqAttach getStandardReqAttach() {
        return this.standardReqAttach;
    }

    public void setStandardReqAttach(StandardReqAttach standardReqAttach) {
        this.standardReqAttach = standardReqAttach;
    }

    public IntrvuReqAttch standardReqAttach(StandardReqAttach standardReqAttach) {
        this.setStandardReqAttach(standardReqAttach);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public IntrvuReqAttch employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntrvuReqAttch)) {
            return false;
        }
        return getId() != null && getId().equals(((IntrvuReqAttch) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntrvuReqAttch{" +
            "id=" + getId() +
            ", addDate='" + getAddDate() + "'" +
            ", attch='" + getAttch() + "'" +
            ", attchContentType='" + getAttchContentType() + "'" +
            "}";
    }
}
