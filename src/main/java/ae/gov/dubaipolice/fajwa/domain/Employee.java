package ae.gov.dubaipolice.fajwa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "grade_name")
    private String gradeName;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "assigment_id")
    private Long assigmentId;

    @Column(name = "date_of_assignment")
    private String dateOfAssignment;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "address")
    private String address;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "jhi_organization")
    private String organization;

    @Column(name = "national_identifier")
    private String nationalIdentifier;

    @Column(name = "jhi_uid")
    private String uid;

    @Column(name = "parent_department_id")
    private String parentDepartmentId;

    @Column(name = "slm_military_flag")
    private Integer slmMilitaryFlag;

    @Column(name = "military_first_name")
    private String militaryFirstName;

    @Column(name = "slm_name")
    private String slmName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_of_hire")
    private Instant dateOfHire;

    @Column(name = "date_of_last_hire")
    private Instant dateOfLastHire;

    @Column(name = "gender")
    private String gender;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "nationality_code")
    private String nationalityCode;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "person_start_date")
    private Instant personStartDate;

    @Column(name = "original_date_of_hire")
    private Instant originalDateOfHire;

    @Column(name = "supervisor_id")
    private String supervisorId;

    @Column(name = "supervisor_full_name")
    private String supervisorFullName;

    @Column(name = "parent_department_name")
    private String parentDepartmentName;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "section_name")
    private String sectionName;

    @Column(name = "adjusted_service_date")
    private Instant adjustedServiceDate;

    @Column(name = "qualification_type")
    private String qualificationType;

    @Column(name = "qualification_specification")
    private String qualificationSpecification;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    @JsonIgnoreProperties(value = { "standardReqAttach", "employee" }, allowSetters = true)
    private Set<IntrvuReqAttch> intrvuReqAttches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Employee fullname(String fullname) {
        this.setFullname(fullname);
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getGradeId() {
        return this.gradeId;
    }

    public Employee gradeId(Long gradeId) {
        this.setGradeId(gradeId);
        return this;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return this.gradeName;
    }

    public Employee gradeName(String gradeName) {
        this.setGradeName(gradeName);
        return this;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public Employee homePhone(String homePhone) {
        this.setHomePhone(homePhone);
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public Employee mobileNumber(String mobileNumber) {
        this.setMobileNumber(mobileNumber);
        return this;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getAssigmentId() {
        return this.assigmentId;
    }

    public Employee assigmentId(Long assigmentId) {
        this.setAssigmentId(assigmentId);
        return this;
    }

    public void setAssigmentId(Long assigmentId) {
        this.assigmentId = assigmentId;
    }

    public String getDateOfAssignment() {
        return this.dateOfAssignment;
    }

    public Employee dateOfAssignment(String dateOfAssignment) {
        this.setDateOfAssignment(dateOfAssignment);
        return this;
    }

    public void setDateOfAssignment(String dateOfAssignment) {
        this.dateOfAssignment = dateOfAssignment;
    }

    public String getJobName() {
        return this.jobName;
    }

    public Employee jobName(String jobName) {
        this.setJobName(jobName);
        return this;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getAddress() {
        return this.address;
    }

    public Employee address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOrganizationId() {
        return this.organizationId;
    }

    public Employee organizationId(Long organizationId) {
        this.setOrganizationId(organizationId);
        return this;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganization() {
        return this.organization;
    }

    public Employee organization(String organization) {
        this.setOrganization(organization);
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getNationalIdentifier() {
        return this.nationalIdentifier;
    }

    public Employee nationalIdentifier(String nationalIdentifier) {
        this.setNationalIdentifier(nationalIdentifier);
        return this;
    }

    public void setNationalIdentifier(String nationalIdentifier) {
        this.nationalIdentifier = nationalIdentifier;
    }

    public String getUid() {
        return this.uid;
    }

    public Employee uid(String uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getParentDepartmentId() {
        return this.parentDepartmentId;
    }

    public Employee parentDepartmentId(String parentDepartmentId) {
        this.setParentDepartmentId(parentDepartmentId);
        return this;
    }

    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public Integer getSlmMilitaryFlag() {
        return this.slmMilitaryFlag;
    }

    public Employee slmMilitaryFlag(Integer slmMilitaryFlag) {
        this.setSlmMilitaryFlag(slmMilitaryFlag);
        return this;
    }

    public void setSlmMilitaryFlag(Integer slmMilitaryFlag) {
        this.slmMilitaryFlag = slmMilitaryFlag;
    }

    public String getMilitaryFirstName() {
        return this.militaryFirstName;
    }

    public Employee militaryFirstName(String militaryFirstName) {
        this.setMilitaryFirstName(militaryFirstName);
        return this;
    }

    public void setMilitaryFirstName(String militaryFirstName) {
        this.militaryFirstName = militaryFirstName;
    }

    public String getSlmName() {
        return this.slmName;
    }

    public Employee slmName(String slmName) {
        this.setSlmName(slmName);
        return this;
    }

    public void setSlmName(String slmName) {
        this.slmName = slmName;
    }

    public Integer getAge() {
        return this.age;
    }

    public Employee age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Instant getDateOfHire() {
        return this.dateOfHire;
    }

    public Employee dateOfHire(Instant dateOfHire) {
        this.setDateOfHire(dateOfHire);
        return this;
    }

    public void setDateOfHire(Instant dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public Instant getDateOfLastHire() {
        return this.dateOfLastHire;
    }

    public Employee dateOfLastHire(Instant dateOfLastHire) {
        this.setDateOfLastHire(dateOfLastHire);
        return this;
    }

    public void setDateOfLastHire(Instant dateOfLastHire) {
        this.dateOfLastHire = dateOfLastHire;
    }

    public String getGender() {
        return this.gender;
    }

    public Employee gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getJobId() {
        return this.jobId;
    }

    public Employee jobId(Long jobId) {
        this.setJobId(jobId);
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getNationalityCode() {
        return this.nationalityCode;
    }

    public Employee nationalityCode(String nationalityCode) {
        this.setNationalityCode(nationalityCode);
        return this;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Employee nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Instant getPersonStartDate() {
        return this.personStartDate;
    }

    public Employee personStartDate(Instant personStartDate) {
        this.setPersonStartDate(personStartDate);
        return this;
    }

    public void setPersonStartDate(Instant personStartDate) {
        this.personStartDate = personStartDate;
    }

    public Instant getOriginalDateOfHire() {
        return this.originalDateOfHire;
    }

    public Employee originalDateOfHire(Instant originalDateOfHire) {
        this.setOriginalDateOfHire(originalDateOfHire);
        return this;
    }

    public void setOriginalDateOfHire(Instant originalDateOfHire) {
        this.originalDateOfHire = originalDateOfHire;
    }

    public String getSupervisorId() {
        return this.supervisorId;
    }

    public Employee supervisorId(String supervisorId) {
        this.setSupervisorId(supervisorId);
        return this;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisorFullName() {
        return this.supervisorFullName;
    }

    public Employee supervisorFullName(String supervisorFullName) {
        this.setSupervisorFullName(supervisorFullName);
        return this;
    }

    public void setSupervisorFullName(String supervisorFullName) {
        this.supervisorFullName = supervisorFullName;
    }

    public String getParentDepartmentName() {
        return this.parentDepartmentName;
    }

    public Employee parentDepartmentName(String parentDepartmentName) {
        this.setParentDepartmentName(parentDepartmentName);
        return this;
    }

    public void setParentDepartmentName(String parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public Employee sectionId(Long sectionId) {
        this.setSectionId(sectionId);
        return this;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return this.sectionName;
    }

    public Employee sectionName(String sectionName) {
        this.setSectionName(sectionName);
        return this;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Instant getAdjustedServiceDate() {
        return this.adjustedServiceDate;
    }

    public Employee adjustedServiceDate(Instant adjustedServiceDate) {
        this.setAdjustedServiceDate(adjustedServiceDate);
        return this;
    }

    public void setAdjustedServiceDate(Instant adjustedServiceDate) {
        this.adjustedServiceDate = adjustedServiceDate;
    }

    public String getQualificationType() {
        return this.qualificationType;
    }

    public Employee qualificationType(String qualificationType) {
        this.setQualificationType(qualificationType);
        return this;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }

    public String getQualificationSpecification() {
        return this.qualificationSpecification;
    }

    public Employee qualificationSpecification(String qualificationSpecification) {
        this.setQualificationSpecification(qualificationSpecification);
        return this;
    }

    public void setQualificationSpecification(String qualificationSpecification) {
        this.qualificationSpecification = qualificationSpecification;
    }

    public Set<IntrvuReqAttch> getIntrvuReqAttches() {
        return this.intrvuReqAttches;
    }

    public void setIntrvuReqAttches(Set<IntrvuReqAttch> intrvuReqAttches) {
        if (this.intrvuReqAttches != null) {
            this.intrvuReqAttches.forEach(i -> i.setEmployee(null));
        }
        if (intrvuReqAttches != null) {
            intrvuReqAttches.forEach(i -> i.setEmployee(this));
        }
        this.intrvuReqAttches = intrvuReqAttches;
    }

    public Employee intrvuReqAttches(Set<IntrvuReqAttch> intrvuReqAttches) {
        this.setIntrvuReqAttches(intrvuReqAttches);
        return this;
    }

    public Employee addIntrvuReqAttch(IntrvuReqAttch intrvuReqAttch) {
        this.intrvuReqAttches.add(intrvuReqAttch);
        intrvuReqAttch.setEmployee(this);
        return this;
    }

    public Employee removeIntrvuReqAttch(IntrvuReqAttch intrvuReqAttch) {
        this.intrvuReqAttches.remove(intrvuReqAttch);
        intrvuReqAttch.setEmployee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return getId() != null && getId().equals(((Employee) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", gradeId=" + getGradeId() +
            ", gradeName='" + getGradeName() + "'" +
            ", homePhone='" + getHomePhone() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", assigmentId=" + getAssigmentId() +
            ", dateOfAssignment='" + getDateOfAssignment() + "'" +
            ", jobName='" + getJobName() + "'" +
            ", address='" + getAddress() + "'" +
            ", organizationId=" + getOrganizationId() +
            ", organization='" + getOrganization() + "'" +
            ", nationalIdentifier='" + getNationalIdentifier() + "'" +
            ", uid='" + getUid() + "'" +
            ", parentDepartmentId='" + getParentDepartmentId() + "'" +
            ", slmMilitaryFlag=" + getSlmMilitaryFlag() +
            ", militaryFirstName='" + getMilitaryFirstName() + "'" +
            ", slmName='" + getSlmName() + "'" +
            ", age=" + getAge() +
            ", dateOfHire='" + getDateOfHire() + "'" +
            ", dateOfLastHire='" + getDateOfLastHire() + "'" +
            ", gender='" + getGender() + "'" +
            ", jobId=" + getJobId() +
            ", nationalityCode='" + getNationalityCode() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", personStartDate='" + getPersonStartDate() + "'" +
            ", originalDateOfHire='" + getOriginalDateOfHire() + "'" +
            ", supervisorId='" + getSupervisorId() + "'" +
            ", supervisorFullName='" + getSupervisorFullName() + "'" +
            ", parentDepartmentName='" + getParentDepartmentName() + "'" +
            ", sectionId=" + getSectionId() +
            ", sectionName='" + getSectionName() + "'" +
            ", adjustedServiceDate='" + getAdjustedServiceDate() + "'" +
            ", qualificationType='" + getQualificationType() + "'" +
            ", qualificationSpecification='" + getQualificationSpecification() + "'" +
            "}";
    }
}
