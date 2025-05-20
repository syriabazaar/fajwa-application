export interface IEmployee {
  id?: number;
  fullname?: string | null;
  gradeId?: number | null;
  gradeName?: string | null;
  homePhone?: string | null;
  mobileNumber?: string | null;
  assigmentId?: number | null;
  dateOfAssignment?: string | null;
  jobName?: string | null;
  address?: string | null;
  organizationId?: number | null;
  organization?: string | null;
  nationalIdentifier?: string | null;
  uid?: string | null;
  parentDepartmentId?: string | null;
  slmMilitaryFlag?: number | null;
  militaryFirstName?: string | null;
  slmName?: string | null;
  age?: number | null;
  dateOfHire?: Date | null;
  dateOfLastHire?: Date | null;
  gender?: string | null;
  jobId?: number | null;
  nationalityCode?: string | null;
  nationality?: string | null;
  personStartDate?: Date | null;
  originalDateOfHire?: Date | null;
  supervisorId?: string | null;
  supervisorFullName?: string | null;
  parentDepartmentName?: string | null;
  sectionId?: number | null;
  sectionName?: string | null;
  adjustedServiceDate?: Date | null;
  qualificationType?: string | null;
  qualificationSpecification?: string | null;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public fullname?: string | null,
    public gradeId?: number | null,
    public gradeName?: string | null,
    public homePhone?: string | null,
    public mobileNumber?: string | null,
    public assigmentId?: number | null,
    public dateOfAssignment?: string | null,
    public jobName?: string | null,
    public address?: string | null,
    public organizationId?: number | null,
    public organization?: string | null,
    public nationalIdentifier?: string | null,
    public uid?: string | null,
    public parentDepartmentId?: string | null,
    public slmMilitaryFlag?: number | null,
    public militaryFirstName?: string | null,
    public slmName?: string | null,
    public age?: number | null,
    public dateOfHire?: Date | null,
    public dateOfLastHire?: Date | null,
    public gender?: string | null,
    public jobId?: number | null,
    public nationalityCode?: string | null,
    public nationality?: string | null,
    public personStartDate?: Date | null,
    public originalDateOfHire?: Date | null,
    public supervisorId?: string | null,
    public supervisorFullName?: string | null,
    public parentDepartmentName?: string | null,
    public sectionId?: number | null,
    public sectionName?: string | null,
    public adjustedServiceDate?: Date | null,
    public qualificationType?: string | null,
    public qualificationSpecification?: string | null,
  ) {}
}
