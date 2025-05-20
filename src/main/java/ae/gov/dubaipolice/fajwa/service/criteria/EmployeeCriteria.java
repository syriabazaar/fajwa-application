package ae.gov.dubaipolice.fajwa.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ae.gov.dubaipolice.fajwa.domain.Employee} entity. This class is used
 * in {@link ae.gov.dubaipolice.fajwa.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullname;

    private LongFilter gradeId;

    private StringFilter gradeName;

    private StringFilter homePhone;

    private StringFilter mobileNumber;

    private LongFilter assigmentId;

    private StringFilter dateOfAssignment;

    private StringFilter jobName;

    private StringFilter address;

    private LongFilter organizationId;

    private StringFilter organization;

    private StringFilter nationalIdentifier;

    private StringFilter uid;

    private StringFilter parentDepartmentId;

    private IntegerFilter slmMilitaryFlag;

    private StringFilter militaryFirstName;

    private StringFilter slmName;

    private IntegerFilter age;

    private InstantFilter dateOfHire;

    private InstantFilter dateOfLastHire;

    private StringFilter gender;

    private LongFilter jobId;

    private StringFilter nationalityCode;

    private StringFilter nationality;

    private InstantFilter personStartDate;

    private InstantFilter originalDateOfHire;

    private StringFilter supervisorId;

    private StringFilter supervisorFullName;

    private StringFilter parentDepartmentName;

    private LongFilter sectionId;

    private StringFilter sectionName;

    private InstantFilter adjustedServiceDate;

    private StringFilter qualificationType;

    private StringFilter qualificationSpecification;

    private LongFilter intrvuReqAttchId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fullname = other.optionalFullname().map(StringFilter::copy).orElse(null);
        this.gradeId = other.optionalGradeId().map(LongFilter::copy).orElse(null);
        this.gradeName = other.optionalGradeName().map(StringFilter::copy).orElse(null);
        this.homePhone = other.optionalHomePhone().map(StringFilter::copy).orElse(null);
        this.mobileNumber = other.optionalMobileNumber().map(StringFilter::copy).orElse(null);
        this.assigmentId = other.optionalAssigmentId().map(LongFilter::copy).orElse(null);
        this.dateOfAssignment = other.optionalDateOfAssignment().map(StringFilter::copy).orElse(null);
        this.jobName = other.optionalJobName().map(StringFilter::copy).orElse(null);
        this.address = other.optionalAddress().map(StringFilter::copy).orElse(null);
        this.organizationId = other.optionalOrganizationId().map(LongFilter::copy).orElse(null);
        this.organization = other.optionalOrganization().map(StringFilter::copy).orElse(null);
        this.nationalIdentifier = other.optionalNationalIdentifier().map(StringFilter::copy).orElse(null);
        this.uid = other.optionalUid().map(StringFilter::copy).orElse(null);
        this.parentDepartmentId = other.optionalParentDepartmentId().map(StringFilter::copy).orElse(null);
        this.slmMilitaryFlag = other.optionalSlmMilitaryFlag().map(IntegerFilter::copy).orElse(null);
        this.militaryFirstName = other.optionalMilitaryFirstName().map(StringFilter::copy).orElse(null);
        this.slmName = other.optionalSlmName().map(StringFilter::copy).orElse(null);
        this.age = other.optionalAge().map(IntegerFilter::copy).orElse(null);
        this.dateOfHire = other.optionalDateOfHire().map(InstantFilter::copy).orElse(null);
        this.dateOfLastHire = other.optionalDateOfLastHire().map(InstantFilter::copy).orElse(null);
        this.gender = other.optionalGender().map(StringFilter::copy).orElse(null);
        this.jobId = other.optionalJobId().map(LongFilter::copy).orElse(null);
        this.nationalityCode = other.optionalNationalityCode().map(StringFilter::copy).orElse(null);
        this.nationality = other.optionalNationality().map(StringFilter::copy).orElse(null);
        this.personStartDate = other.optionalPersonStartDate().map(InstantFilter::copy).orElse(null);
        this.originalDateOfHire = other.optionalOriginalDateOfHire().map(InstantFilter::copy).orElse(null);
        this.supervisorId = other.optionalSupervisorId().map(StringFilter::copy).orElse(null);
        this.supervisorFullName = other.optionalSupervisorFullName().map(StringFilter::copy).orElse(null);
        this.parentDepartmentName = other.optionalParentDepartmentName().map(StringFilter::copy).orElse(null);
        this.sectionId = other.optionalSectionId().map(LongFilter::copy).orElse(null);
        this.sectionName = other.optionalSectionName().map(StringFilter::copy).orElse(null);
        this.adjustedServiceDate = other.optionalAdjustedServiceDate().map(InstantFilter::copy).orElse(null);
        this.qualificationType = other.optionalQualificationType().map(StringFilter::copy).orElse(null);
        this.qualificationSpecification = other.optionalQualificationSpecification().map(StringFilter::copy).orElse(null);
        this.intrvuReqAttchId = other.optionalIntrvuReqAttchId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullname() {
        return fullname;
    }

    public Optional<StringFilter> optionalFullname() {
        return Optional.ofNullable(fullname);
    }

    public StringFilter fullname() {
        if (fullname == null) {
            setFullname(new StringFilter());
        }
        return fullname;
    }

    public void setFullname(StringFilter fullname) {
        this.fullname = fullname;
    }

    public LongFilter getGradeId() {
        return gradeId;
    }

    public Optional<LongFilter> optionalGradeId() {
        return Optional.ofNullable(gradeId);
    }

    public LongFilter gradeId() {
        if (gradeId == null) {
            setGradeId(new LongFilter());
        }
        return gradeId;
    }

    public void setGradeId(LongFilter gradeId) {
        this.gradeId = gradeId;
    }

    public StringFilter getGradeName() {
        return gradeName;
    }

    public Optional<StringFilter> optionalGradeName() {
        return Optional.ofNullable(gradeName);
    }

    public StringFilter gradeName() {
        if (gradeName == null) {
            setGradeName(new StringFilter());
        }
        return gradeName;
    }

    public void setGradeName(StringFilter gradeName) {
        this.gradeName = gradeName;
    }

    public StringFilter getHomePhone() {
        return homePhone;
    }

    public Optional<StringFilter> optionalHomePhone() {
        return Optional.ofNullable(homePhone);
    }

    public StringFilter homePhone() {
        if (homePhone == null) {
            setHomePhone(new StringFilter());
        }
        return homePhone;
    }

    public void setHomePhone(StringFilter homePhone) {
        this.homePhone = homePhone;
    }

    public StringFilter getMobileNumber() {
        return mobileNumber;
    }

    public Optional<StringFilter> optionalMobileNumber() {
        return Optional.ofNullable(mobileNumber);
    }

    public StringFilter mobileNumber() {
        if (mobileNumber == null) {
            setMobileNumber(new StringFilter());
        }
        return mobileNumber;
    }

    public void setMobileNumber(StringFilter mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public LongFilter getAssigmentId() {
        return assigmentId;
    }

    public Optional<LongFilter> optionalAssigmentId() {
        return Optional.ofNullable(assigmentId);
    }

    public LongFilter assigmentId() {
        if (assigmentId == null) {
            setAssigmentId(new LongFilter());
        }
        return assigmentId;
    }

    public void setAssigmentId(LongFilter assigmentId) {
        this.assigmentId = assigmentId;
    }

    public StringFilter getDateOfAssignment() {
        return dateOfAssignment;
    }

    public Optional<StringFilter> optionalDateOfAssignment() {
        return Optional.ofNullable(dateOfAssignment);
    }

    public StringFilter dateOfAssignment() {
        if (dateOfAssignment == null) {
            setDateOfAssignment(new StringFilter());
        }
        return dateOfAssignment;
    }

    public void setDateOfAssignment(StringFilter dateOfAssignment) {
        this.dateOfAssignment = dateOfAssignment;
    }

    public StringFilter getJobName() {
        return jobName;
    }

    public Optional<StringFilter> optionalJobName() {
        return Optional.ofNullable(jobName);
    }

    public StringFilter jobName() {
        if (jobName == null) {
            setJobName(new StringFilter());
        }
        return jobName;
    }

    public void setJobName(StringFilter jobName) {
        this.jobName = jobName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public Optional<StringFilter> optionalAddress() {
        return Optional.ofNullable(address);
    }

    public StringFilter address() {
        if (address == null) {
            setAddress(new StringFilter());
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public Optional<LongFilter> optionalOrganizationId() {
        return Optional.ofNullable(organizationId);
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            setOrganizationId(new LongFilter());
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public Optional<StringFilter> optionalOrganization() {
        return Optional.ofNullable(organization);
    }

    public StringFilter organization() {
        if (organization == null) {
            setOrganization(new StringFilter());
        }
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public StringFilter getNationalIdentifier() {
        return nationalIdentifier;
    }

    public Optional<StringFilter> optionalNationalIdentifier() {
        return Optional.ofNullable(nationalIdentifier);
    }

    public StringFilter nationalIdentifier() {
        if (nationalIdentifier == null) {
            setNationalIdentifier(new StringFilter());
        }
        return nationalIdentifier;
    }

    public void setNationalIdentifier(StringFilter nationalIdentifier) {
        this.nationalIdentifier = nationalIdentifier;
    }

    public StringFilter getUid() {
        return uid;
    }

    public Optional<StringFilter> optionalUid() {
        return Optional.ofNullable(uid);
    }

    public StringFilter uid() {
        if (uid == null) {
            setUid(new StringFilter());
        }
        return uid;
    }

    public void setUid(StringFilter uid) {
        this.uid = uid;
    }

    public StringFilter getParentDepartmentId() {
        return parentDepartmentId;
    }

    public Optional<StringFilter> optionalParentDepartmentId() {
        return Optional.ofNullable(parentDepartmentId);
    }

    public StringFilter parentDepartmentId() {
        if (parentDepartmentId == null) {
            setParentDepartmentId(new StringFilter());
        }
        return parentDepartmentId;
    }

    public void setParentDepartmentId(StringFilter parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public IntegerFilter getSlmMilitaryFlag() {
        return slmMilitaryFlag;
    }

    public Optional<IntegerFilter> optionalSlmMilitaryFlag() {
        return Optional.ofNullable(slmMilitaryFlag);
    }

    public IntegerFilter slmMilitaryFlag() {
        if (slmMilitaryFlag == null) {
            setSlmMilitaryFlag(new IntegerFilter());
        }
        return slmMilitaryFlag;
    }

    public void setSlmMilitaryFlag(IntegerFilter slmMilitaryFlag) {
        this.slmMilitaryFlag = slmMilitaryFlag;
    }

    public StringFilter getMilitaryFirstName() {
        return militaryFirstName;
    }

    public Optional<StringFilter> optionalMilitaryFirstName() {
        return Optional.ofNullable(militaryFirstName);
    }

    public StringFilter militaryFirstName() {
        if (militaryFirstName == null) {
            setMilitaryFirstName(new StringFilter());
        }
        return militaryFirstName;
    }

    public void setMilitaryFirstName(StringFilter militaryFirstName) {
        this.militaryFirstName = militaryFirstName;
    }

    public StringFilter getSlmName() {
        return slmName;
    }

    public Optional<StringFilter> optionalSlmName() {
        return Optional.ofNullable(slmName);
    }

    public StringFilter slmName() {
        if (slmName == null) {
            setSlmName(new StringFilter());
        }
        return slmName;
    }

    public void setSlmName(StringFilter slmName) {
        this.slmName = slmName;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public Optional<IntegerFilter> optionalAge() {
        return Optional.ofNullable(age);
    }

    public IntegerFilter age() {
        if (age == null) {
            setAge(new IntegerFilter());
        }
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public InstantFilter getDateOfHire() {
        return dateOfHire;
    }

    public Optional<InstantFilter> optionalDateOfHire() {
        return Optional.ofNullable(dateOfHire);
    }

    public InstantFilter dateOfHire() {
        if (dateOfHire == null) {
            setDateOfHire(new InstantFilter());
        }
        return dateOfHire;
    }

    public void setDateOfHire(InstantFilter dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public InstantFilter getDateOfLastHire() {
        return dateOfLastHire;
    }

    public Optional<InstantFilter> optionalDateOfLastHire() {
        return Optional.ofNullable(dateOfLastHire);
    }

    public InstantFilter dateOfLastHire() {
        if (dateOfLastHire == null) {
            setDateOfLastHire(new InstantFilter());
        }
        return dateOfLastHire;
    }

    public void setDateOfLastHire(InstantFilter dateOfLastHire) {
        this.dateOfLastHire = dateOfLastHire;
    }

    public StringFilter getGender() {
        return gender;
    }

    public Optional<StringFilter> optionalGender() {
        return Optional.ofNullable(gender);
    }

    public StringFilter gender() {
        if (gender == null) {
            setGender(new StringFilter());
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public Optional<LongFilter> optionalJobId() {
        return Optional.ofNullable(jobId);
    }

    public LongFilter jobId() {
        if (jobId == null) {
            setJobId(new LongFilter());
        }
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }

    public StringFilter getNationalityCode() {
        return nationalityCode;
    }

    public Optional<StringFilter> optionalNationalityCode() {
        return Optional.ofNullable(nationalityCode);
    }

    public StringFilter nationalityCode() {
        if (nationalityCode == null) {
            setNationalityCode(new StringFilter());
        }
        return nationalityCode;
    }

    public void setNationalityCode(StringFilter nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public Optional<StringFilter> optionalNationality() {
        return Optional.ofNullable(nationality);
    }

    public StringFilter nationality() {
        if (nationality == null) {
            setNationality(new StringFilter());
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public InstantFilter getPersonStartDate() {
        return personStartDate;
    }

    public Optional<InstantFilter> optionalPersonStartDate() {
        return Optional.ofNullable(personStartDate);
    }

    public InstantFilter personStartDate() {
        if (personStartDate == null) {
            setPersonStartDate(new InstantFilter());
        }
        return personStartDate;
    }

    public void setPersonStartDate(InstantFilter personStartDate) {
        this.personStartDate = personStartDate;
    }

    public InstantFilter getOriginalDateOfHire() {
        return originalDateOfHire;
    }

    public Optional<InstantFilter> optionalOriginalDateOfHire() {
        return Optional.ofNullable(originalDateOfHire);
    }

    public InstantFilter originalDateOfHire() {
        if (originalDateOfHire == null) {
            setOriginalDateOfHire(new InstantFilter());
        }
        return originalDateOfHire;
    }

    public void setOriginalDateOfHire(InstantFilter originalDateOfHire) {
        this.originalDateOfHire = originalDateOfHire;
    }

    public StringFilter getSupervisorId() {
        return supervisorId;
    }

    public Optional<StringFilter> optionalSupervisorId() {
        return Optional.ofNullable(supervisorId);
    }

    public StringFilter supervisorId() {
        if (supervisorId == null) {
            setSupervisorId(new StringFilter());
        }
        return supervisorId;
    }

    public void setSupervisorId(StringFilter supervisorId) {
        this.supervisorId = supervisorId;
    }

    public StringFilter getSupervisorFullName() {
        return supervisorFullName;
    }

    public Optional<StringFilter> optionalSupervisorFullName() {
        return Optional.ofNullable(supervisorFullName);
    }

    public StringFilter supervisorFullName() {
        if (supervisorFullName == null) {
            setSupervisorFullName(new StringFilter());
        }
        return supervisorFullName;
    }

    public void setSupervisorFullName(StringFilter supervisorFullName) {
        this.supervisorFullName = supervisorFullName;
    }

    public StringFilter getParentDepartmentName() {
        return parentDepartmentName;
    }

    public Optional<StringFilter> optionalParentDepartmentName() {
        return Optional.ofNullable(parentDepartmentName);
    }

    public StringFilter parentDepartmentName() {
        if (parentDepartmentName == null) {
            setParentDepartmentName(new StringFilter());
        }
        return parentDepartmentName;
    }

    public void setParentDepartmentName(StringFilter parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public LongFilter getSectionId() {
        return sectionId;
    }

    public Optional<LongFilter> optionalSectionId() {
        return Optional.ofNullable(sectionId);
    }

    public LongFilter sectionId() {
        if (sectionId == null) {
            setSectionId(new LongFilter());
        }
        return sectionId;
    }

    public void setSectionId(LongFilter sectionId) {
        this.sectionId = sectionId;
    }

    public StringFilter getSectionName() {
        return sectionName;
    }

    public Optional<StringFilter> optionalSectionName() {
        return Optional.ofNullable(sectionName);
    }

    public StringFilter sectionName() {
        if (sectionName == null) {
            setSectionName(new StringFilter());
        }
        return sectionName;
    }

    public void setSectionName(StringFilter sectionName) {
        this.sectionName = sectionName;
    }

    public InstantFilter getAdjustedServiceDate() {
        return adjustedServiceDate;
    }

    public Optional<InstantFilter> optionalAdjustedServiceDate() {
        return Optional.ofNullable(adjustedServiceDate);
    }

    public InstantFilter adjustedServiceDate() {
        if (adjustedServiceDate == null) {
            setAdjustedServiceDate(new InstantFilter());
        }
        return adjustedServiceDate;
    }

    public void setAdjustedServiceDate(InstantFilter adjustedServiceDate) {
        this.adjustedServiceDate = adjustedServiceDate;
    }

    public StringFilter getQualificationType() {
        return qualificationType;
    }

    public Optional<StringFilter> optionalQualificationType() {
        return Optional.ofNullable(qualificationType);
    }

    public StringFilter qualificationType() {
        if (qualificationType == null) {
            setQualificationType(new StringFilter());
        }
        return qualificationType;
    }

    public void setQualificationType(StringFilter qualificationType) {
        this.qualificationType = qualificationType;
    }

    public StringFilter getQualificationSpecification() {
        return qualificationSpecification;
    }

    public Optional<StringFilter> optionalQualificationSpecification() {
        return Optional.ofNullable(qualificationSpecification);
    }

    public StringFilter qualificationSpecification() {
        if (qualificationSpecification == null) {
            setQualificationSpecification(new StringFilter());
        }
        return qualificationSpecification;
    }

    public void setQualificationSpecification(StringFilter qualificationSpecification) {
        this.qualificationSpecification = qualificationSpecification;
    }

    public LongFilter getIntrvuReqAttchId() {
        return intrvuReqAttchId;
    }

    public Optional<LongFilter> optionalIntrvuReqAttchId() {
        return Optional.ofNullable(intrvuReqAttchId);
    }

    public LongFilter intrvuReqAttchId() {
        if (intrvuReqAttchId == null) {
            setIntrvuReqAttchId(new LongFilter());
        }
        return intrvuReqAttchId;
    }

    public void setIntrvuReqAttchId(LongFilter intrvuReqAttchId) {
        this.intrvuReqAttchId = intrvuReqAttchId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullname, that.fullname) &&
            Objects.equals(gradeId, that.gradeId) &&
            Objects.equals(gradeName, that.gradeName) &&
            Objects.equals(homePhone, that.homePhone) &&
            Objects.equals(mobileNumber, that.mobileNumber) &&
            Objects.equals(assigmentId, that.assigmentId) &&
            Objects.equals(dateOfAssignment, that.dateOfAssignment) &&
            Objects.equals(jobName, that.jobName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(nationalIdentifier, that.nationalIdentifier) &&
            Objects.equals(uid, that.uid) &&
            Objects.equals(parentDepartmentId, that.parentDepartmentId) &&
            Objects.equals(slmMilitaryFlag, that.slmMilitaryFlag) &&
            Objects.equals(militaryFirstName, that.militaryFirstName) &&
            Objects.equals(slmName, that.slmName) &&
            Objects.equals(age, that.age) &&
            Objects.equals(dateOfHire, that.dateOfHire) &&
            Objects.equals(dateOfLastHire, that.dateOfLastHire) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(jobId, that.jobId) &&
            Objects.equals(nationalityCode, that.nationalityCode) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(personStartDate, that.personStartDate) &&
            Objects.equals(originalDateOfHire, that.originalDateOfHire) &&
            Objects.equals(supervisorId, that.supervisorId) &&
            Objects.equals(supervisorFullName, that.supervisorFullName) &&
            Objects.equals(parentDepartmentName, that.parentDepartmentName) &&
            Objects.equals(sectionId, that.sectionId) &&
            Objects.equals(sectionName, that.sectionName) &&
            Objects.equals(adjustedServiceDate, that.adjustedServiceDate) &&
            Objects.equals(qualificationType, that.qualificationType) &&
            Objects.equals(qualificationSpecification, that.qualificationSpecification) &&
            Objects.equals(intrvuReqAttchId, that.intrvuReqAttchId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            fullname,
            gradeId,
            gradeName,
            homePhone,
            mobileNumber,
            assigmentId,
            dateOfAssignment,
            jobName,
            address,
            organizationId,
            organization,
            nationalIdentifier,
            uid,
            parentDepartmentId,
            slmMilitaryFlag,
            militaryFirstName,
            slmName,
            age,
            dateOfHire,
            dateOfLastHire,
            gender,
            jobId,
            nationalityCode,
            nationality,
            personStartDate,
            originalDateOfHire,
            supervisorId,
            supervisorFullName,
            parentDepartmentName,
            sectionId,
            sectionName,
            adjustedServiceDate,
            qualificationType,
            qualificationSpecification,
            intrvuReqAttchId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFullname().map(f -> "fullname=" + f + ", ").orElse("") +
            optionalGradeId().map(f -> "gradeId=" + f + ", ").orElse("") +
            optionalGradeName().map(f -> "gradeName=" + f + ", ").orElse("") +
            optionalHomePhone().map(f -> "homePhone=" + f + ", ").orElse("") +
            optionalMobileNumber().map(f -> "mobileNumber=" + f + ", ").orElse("") +
            optionalAssigmentId().map(f -> "assigmentId=" + f + ", ").orElse("") +
            optionalDateOfAssignment().map(f -> "dateOfAssignment=" + f + ", ").orElse("") +
            optionalJobName().map(f -> "jobName=" + f + ", ").orElse("") +
            optionalAddress().map(f -> "address=" + f + ", ").orElse("") +
            optionalOrganizationId().map(f -> "organizationId=" + f + ", ").orElse("") +
            optionalOrganization().map(f -> "organization=" + f + ", ").orElse("") +
            optionalNationalIdentifier().map(f -> "nationalIdentifier=" + f + ", ").orElse("") +
            optionalUid().map(f -> "uid=" + f + ", ").orElse("") +
            optionalParentDepartmentId().map(f -> "parentDepartmentId=" + f + ", ").orElse("") +
            optionalSlmMilitaryFlag().map(f -> "slmMilitaryFlag=" + f + ", ").orElse("") +
            optionalMilitaryFirstName().map(f -> "militaryFirstName=" + f + ", ").orElse("") +
            optionalSlmName().map(f -> "slmName=" + f + ", ").orElse("") +
            optionalAge().map(f -> "age=" + f + ", ").orElse("") +
            optionalDateOfHire().map(f -> "dateOfHire=" + f + ", ").orElse("") +
            optionalDateOfLastHire().map(f -> "dateOfLastHire=" + f + ", ").orElse("") +
            optionalGender().map(f -> "gender=" + f + ", ").orElse("") +
            optionalJobId().map(f -> "jobId=" + f + ", ").orElse("") +
            optionalNationalityCode().map(f -> "nationalityCode=" + f + ", ").orElse("") +
            optionalNationality().map(f -> "nationality=" + f + ", ").orElse("") +
            optionalPersonStartDate().map(f -> "personStartDate=" + f + ", ").orElse("") +
            optionalOriginalDateOfHire().map(f -> "originalDateOfHire=" + f + ", ").orElse("") +
            optionalSupervisorId().map(f -> "supervisorId=" + f + ", ").orElse("") +
            optionalSupervisorFullName().map(f -> "supervisorFullName=" + f + ", ").orElse("") +
            optionalParentDepartmentName().map(f -> "parentDepartmentName=" + f + ", ").orElse("") +
            optionalSectionId().map(f -> "sectionId=" + f + ", ").orElse("") +
            optionalSectionName().map(f -> "sectionName=" + f + ", ").orElse("") +
            optionalAdjustedServiceDate().map(f -> "adjustedServiceDate=" + f + ", ").orElse("") +
            optionalQualificationType().map(f -> "qualificationType=" + f + ", ").orElse("") +
            optionalQualificationSpecification().map(f -> "qualificationSpecification=" + f + ", ").orElse("") +
            optionalIntrvuReqAttchId().map(f -> "intrvuReqAttchId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
