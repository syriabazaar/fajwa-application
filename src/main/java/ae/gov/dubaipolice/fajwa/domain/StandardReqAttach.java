package ae.gov.dubaipolice.fajwa.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A StandardReqAttach.
 */
@Entity
@Table(name = "standard_req_attach")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandardReqAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "att_desc")
    private String attDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StandardReqAttach id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public StandardReqAttach name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttDesc() {
        return this.attDesc;
    }

    public StandardReqAttach attDesc(String attDesc) {
        this.setAttDesc(attDesc);
        return this;
    }

    public void setAttDesc(String attDesc) {
        this.attDesc = attDesc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardReqAttach)) {
            return false;
        }
        return getId() != null && getId().equals(((StandardReqAttach) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandardReqAttach{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", attDesc='" + getAttDesc() + "'" +
            "}";
    }
}
