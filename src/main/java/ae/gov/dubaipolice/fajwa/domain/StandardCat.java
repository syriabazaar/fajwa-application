package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A StandardCat.
 */
@Entity
@Table(name = "standard_cat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandardCat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "standardType" }, allowSetters = true)
    private StandardParent standardParent;

    @ManyToOne(fetch = FetchType.LAZY)
    private StandardReqAttach reqAttachment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StandardCat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public StandardCat name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StandardParent getStandardParent() {
        return this.standardParent;
    }

    public void setStandardParent(StandardParent standardParent) {
        this.standardParent = standardParent;
    }

    public StandardCat standardParent(StandardParent standardParent) {
        this.setStandardParent(standardParent);
        return this;
    }

    public StandardReqAttach getReqAttachment() {
        return this.reqAttachment;
    }

    public void setReqAttachment(StandardReqAttach standardReqAttach) {
        this.reqAttachment = standardReqAttach;
    }

    public StandardCat reqAttachment(StandardReqAttach standardReqAttach) {
        this.setReqAttachment(standardReqAttach);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardCat)) {
            return false;
        }
        return getId() != null && getId().equals(((StandardCat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandardCat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
