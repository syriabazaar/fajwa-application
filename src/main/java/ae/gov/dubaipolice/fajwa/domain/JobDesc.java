package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A JobDesc.
 */
@Entity
@Table(name = "job_desc")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JobDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "job_name")
    private String jobName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "jobDescs" }, allowSetters = true)
    private StandardType standardType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JobDesc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return this.jobName;
    }

    public JobDesc jobName(String jobName) {
        this.setJobName(jobName);
        return this;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public StandardType getStandardType() {
        return this.standardType;
    }

    public void setStandardType(StandardType standardType) {
        this.standardType = standardType;
    }

    public JobDesc standardType(StandardType standardType) {
        this.setStandardType(standardType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDesc)) {
            return false;
        }
        return getId() != null && getId().equals(((JobDesc) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobDesc{" +
            "id=" + getId() +
            ", jobName='" + getJobName() + "'" +
            "}";
    }
}
