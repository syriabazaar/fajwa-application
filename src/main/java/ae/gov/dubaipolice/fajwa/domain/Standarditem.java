package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Standarditem.
 */
@Entity
@Table(name = "standarditem")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Standarditem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "weight_percentage")
    private Float weightPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "standardParent", "reqAttachment" }, allowSetters = true)
    private StandardCat standardCat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "standarditem")
    @JsonIgnoreProperties(value = { "standarditem", "appointment" }, allowSetters = true)
    private Set<IntrvuStrdDtls> intrvuStrdDtls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Standarditem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Standarditem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Standarditem isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Float getWeightPercentage() {
        return this.weightPercentage;
    }

    public Standarditem weightPercentage(Float weightPercentage) {
        this.setWeightPercentage(weightPercentage);
        return this;
    }

    public void setWeightPercentage(Float weightPercentage) {
        this.weightPercentage = weightPercentage;
    }

    public StandardCat getStandardCat() {
        return this.standardCat;
    }

    public void setStandardCat(StandardCat standardCat) {
        this.standardCat = standardCat;
    }

    public Standarditem standardCat(StandardCat standardCat) {
        this.setStandardCat(standardCat);
        return this;
    }

    public Set<IntrvuStrdDtls> getIntrvuStrdDtls() {
        return this.intrvuStrdDtls;
    }

    public void setIntrvuStrdDtls(Set<IntrvuStrdDtls> intrvuStrdDtls) {
        if (this.intrvuStrdDtls != null) {
            this.intrvuStrdDtls.forEach(i -> i.setStandarditem(null));
        }
        if (intrvuStrdDtls != null) {
            intrvuStrdDtls.forEach(i -> i.setStandarditem(this));
        }
        this.intrvuStrdDtls = intrvuStrdDtls;
    }

    public Standarditem intrvuStrdDtls(Set<IntrvuStrdDtls> intrvuStrdDtls) {
        this.setIntrvuStrdDtls(intrvuStrdDtls);
        return this;
    }

    public Standarditem addIntrvuStrdDtls(IntrvuStrdDtls intrvuStrdDtls) {
        this.intrvuStrdDtls.add(intrvuStrdDtls);
        intrvuStrdDtls.setStandarditem(this);
        return this;
    }

    public Standarditem removeIntrvuStrdDtls(IntrvuStrdDtls intrvuStrdDtls) {
        this.intrvuStrdDtls.remove(intrvuStrdDtls);
        intrvuStrdDtls.setStandarditem(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Standarditem)) {
            return false;
        }
        return getId() != null && getId().equals(((Standarditem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Standarditem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", weightPercentage=" + getWeightPercentage() +
            "}";
    }
}
