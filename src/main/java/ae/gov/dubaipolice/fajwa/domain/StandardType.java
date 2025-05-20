package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A StandardType.
 */
@Entity
@Table(name = "standard_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StandardType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "standardType")
    @JsonIgnoreProperties(value = { "standardType" }, allowSetters = true)
    private Set<JobDesc> jobDescs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StandardType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public StandardType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<JobDesc> getJobDescs() {
        return this.jobDescs;
    }

    public void setJobDescs(Set<JobDesc> jobDescs) {
        if (this.jobDescs != null) {
            this.jobDescs.forEach(i -> i.setStandardType(null));
        }
        if (jobDescs != null) {
            jobDescs.forEach(i -> i.setStandardType(this));
        }
        this.jobDescs = jobDescs;
    }

    public StandardType jobDescs(Set<JobDesc> jobDescs) {
        this.setJobDescs(jobDescs);
        return this;
    }

    public StandardType addJobDesc(JobDesc jobDesc) {
        this.jobDescs.add(jobDesc);
        jobDesc.setStandardType(this);
        return this;
    }

    public StandardType removeJobDesc(JobDesc jobDesc) {
        this.jobDescs.remove(jobDesc);
        jobDesc.setStandardType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StandardType)) {
            return false;
        }
        return getId() != null && getId().equals(((StandardType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StandardType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
