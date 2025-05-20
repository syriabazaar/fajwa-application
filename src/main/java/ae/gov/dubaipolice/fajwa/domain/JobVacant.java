package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A JobVacant.
 */
@Entity
@Table(name = "job_vacant")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobVacant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "standardType" }, allowSetters = true)
    private JobDesc jobDesc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobVacant")
    @JsonIgnoreProperties(value = { "employee", "jobVacant", "appointment" }, allowSetters = true)
    private Set<Nomination> nominations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobVacant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobVacant department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public JobDesc getJobDesc() {
        return this.jobDesc;
    }

    public void setJobDesc(JobDesc jobDesc) {
        this.jobDesc = jobDesc;
    }

    public JobVacant jobDesc(JobDesc jobDesc) {
        this.setJobDesc(jobDesc);
        return this;
    }

    public Set<Nomination> getNominations() {
        return this.nominations;
    }

    public void setNominations(Set<Nomination> nominations) {
        if (this.nominations != null) {
            this.nominations.forEach(i -> i.setJobVacant(null));
        }
        if (nominations != null) {
            nominations.forEach(i -> i.setJobVacant(this));
        }
        this.nominations = nominations;
    }

    public JobVacant nominations(Set<Nomination> nominations) {
        this.setNominations(nominations);
        return this;
    }

    public JobVacant addNomination(Nomination nomination) {
        this.nominations.add(nomination);
        nomination.setJobVacant(this);
        return this;
    }

    public JobVacant removeNomination(Nomination nomination) {
        this.nominations.remove(nomination);
        nomination.setJobVacant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobVacant)) {
            return false;
        }
        return getId() != null && getId().equals(((JobVacant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobVacant{" +
            "id=" + getId() +
            "}";
    }
}
