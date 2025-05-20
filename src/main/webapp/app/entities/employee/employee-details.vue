<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="employee">
        <h2 class="jh-entity-heading" data-cy="employeeDetailsHeading"><span>Employee</span> {{ employee.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Fullname</span>
          </dt>
          <dd>
            <span>{{ employee.fullname }}</span>
          </dd>
          <dt>
            <span>Grade Id</span>
          </dt>
          <dd>
            <span>{{ employee.gradeId }}</span>
          </dd>
          <dt>
            <span>Grade Name</span>
          </dt>
          <dd>
            <span>{{ employee.gradeName }}</span>
          </dd>
          <dt>
            <span>Home Phone</span>
          </dt>
          <dd>
            <span>{{ employee.homePhone }}</span>
          </dd>
          <dt>
            <span>Mobile Number</span>
          </dt>
          <dd>
            <span>{{ employee.mobileNumber }}</span>
          </dd>
          <dt>
            <span>Assigment Id</span>
          </dt>
          <dd>
            <span>{{ employee.assigmentId }}</span>
          </dd>
          <dt>
            <span>Date Of Assignment</span>
          </dt>
          <dd>
            <span>{{ employee.dateOfAssignment }}</span>
          </dd>
          <dt>
            <span>Job Name</span>
          </dt>
          <dd>
            <span>{{ employee.jobName }}</span>
          </dd>
          <dt>
            <span>Address</span>
          </dt>
          <dd>
            <span>{{ employee.address }}</span>
          </dd>
          <dt>
            <span>Organization Id</span>
          </dt>
          <dd>
            <span>{{ employee.organizationId }}</span>
          </dd>
          <dt>
            <span>Organization</span>
          </dt>
          <dd>
            <span>{{ employee.organization }}</span>
          </dd>
          <dt>
            <span>National Identifier</span>
          </dt>
          <dd>
            <span>{{ employee.nationalIdentifier }}</span>
          </dd>
          <dt>
            <span>Uid</span>
          </dt>
          <dd>
            <span>{{ employee.uid }}</span>
          </dd>
          <dt>
            <span>Parent Department Id</span>
          </dt>
          <dd>
            <span>{{ employee.parentDepartmentId }}</span>
          </dd>
          <dt>
            <span>Slm Military Flag</span>
          </dt>
          <dd>
            <span>{{ employee.slmMilitaryFlag }}</span>
          </dd>
          <dt>
            <span>Military First Name</span>
          </dt>
          <dd>
            <span>{{ employee.militaryFirstName }}</span>
          </dd>
          <dt>
            <span>Slm Name</span>
          </dt>
          <dd>
            <span>{{ employee.slmName }}</span>
          </dd>
          <dt>
            <span>Age</span>
          </dt>
          <dd>
            <span>{{ employee.age }}</span>
          </dd>
          <dt>
            <span>Date Of Hire</span>
          </dt>
          <dd>
            <span v-if="employee.dateOfHire">{{ formatDateLong(employee.dateOfHire) }}</span>
          </dd>
          <dt>
            <span>Date Of Last Hire</span>
          </dt>
          <dd>
            <span v-if="employee.dateOfLastHire">{{ formatDateLong(employee.dateOfLastHire) }}</span>
          </dd>
          <dt>
            <span>Gender</span>
          </dt>
          <dd>
            <span>{{ employee.gender }}</span>
          </dd>
          <dt>
            <span>Job Id</span>
          </dt>
          <dd>
            <span>{{ employee.jobId }}</span>
          </dd>
          <dt>
            <span>Nationality Code</span>
          </dt>
          <dd>
            <span>{{ employee.nationalityCode }}</span>
          </dd>
          <dt>
            <span>Nationality</span>
          </dt>
          <dd>
            <span>{{ employee.nationality }}</span>
          </dd>
          <dt>
            <span>Person Start Date</span>
          </dt>
          <dd>
            <span v-if="employee.personStartDate">{{ formatDateLong(employee.personStartDate) }}</span>
          </dd>
          <dt>
            <span>Original Date Of Hire</span>
          </dt>
          <dd>
            <span v-if="employee.originalDateOfHire">{{ formatDateLong(employee.originalDateOfHire) }}</span>
          </dd>
          <dt>
            <span>Supervisor Id</span>
          </dt>
          <dd>
            <span>{{ employee.supervisorId }}</span>
          </dd>
          <dt>
            <span>Supervisor Full Name</span>
          </dt>
          <dd>
            <span>{{ employee.supervisorFullName }}</span>
          </dd>
          <dt>
            <span>Parent Department Name</span>
          </dt>
          <dd>
            <span>{{ employee.parentDepartmentName }}</span>
          </dd>
          <dt>
            <span>Section Id</span>
          </dt>
          <dd>
            <span>{{ employee.sectionId }}</span>
          </dd>
          <dt>
            <span>Section Name</span>
          </dt>
          <dd>
            <span>{{ employee.sectionName }}</span>
          </dd>
          <dt>
            <span>Adjusted Service Date</span>
          </dt>
          <dd>
            <span v-if="employee.adjustedServiceDate">{{ formatDateLong(employee.adjustedServiceDate) }}</span>
          </dd>
          <dt>
            <span>Qualification Type</span>
          </dt>
          <dd>
            <span>{{ employee.qualificationType }}</span>
          </dd>
          <dt>
            <span>Qualification Specification</span>
          </dt>
          <dd>
            <span>{{ employee.qualificationSpecification }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./employee-details.component.ts"></script>
