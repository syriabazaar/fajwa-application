<template>
  <div>
    <h2 id="page-heading" data-cy="EmployeeHeading">
      <span id="employee-heading">Employees</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" @click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && employees && employees.length === 0">
      <span>No Employees found</span>
    </div>
    <div class="table-responsive" v-if="employees && employees.length > 0">
      <table class="table table-striped" aria-describedby="employees">
        <thead>
          <tr>
            <th scope="row" @click="changeOrder('id')">
              <span>ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('fullname')">
              <span>Fullname</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fullname'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('gradeId')">
              <span>Grade Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gradeId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('gradeName')">
              <span>Grade Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gradeName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('homePhone')">
              <span>Home Phone</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'homePhone'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('mobileNumber')">
              <span>Mobile Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mobileNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('assigmentId')">
              <span>Assigment Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'assigmentId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('dateOfAssignment')">
              <span>Date Of Assignment</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateOfAssignment'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('jobName')">
              <span>Job Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'jobName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('address')">
              <span>Address</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('organizationId')">
              <span>Organization Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organizationId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('organization')">
              <span>Organization</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'organization'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nationalIdentifier')">
              <span>National Identifier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nationalIdentifier'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('uid')">
              <span>Uid</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'uid'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('parentDepartmentId')">
              <span>Parent Department Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentDepartmentId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('slmMilitaryFlag')">
              <span>Slm Military Flag</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'slmMilitaryFlag'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('militaryFirstName')">
              <span>Military First Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'militaryFirstName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('slmName')">
              <span>Slm Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'slmName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('age')">
              <span>Age</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'age'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('dateOfHire')">
              <span>Date Of Hire</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateOfHire'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('dateOfLastHire')">
              <span>Date Of Last Hire</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'dateOfLastHire'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('gender')">
              <span>Gender</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'gender'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('jobId')">
              <span>Job Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'jobId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nationalityCode')">
              <span>Nationality Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nationalityCode'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('nationality')">
              <span>Nationality</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nationality'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('personStartDate')">
              <span>Person Start Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'personStartDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('originalDateOfHire')">
              <span>Original Date Of Hire</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'originalDateOfHire'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('supervisorId')">
              <span>Supervisor Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supervisorId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('supervisorFullName')">
              <span>Supervisor Full Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supervisorFullName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('parentDepartmentName')">
              <span>Parent Department Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parentDepartmentName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('sectionId')">
              <span>Section Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sectionId'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('sectionName')">
              <span>Section Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sectionName'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('adjustedServiceDate')">
              <span>Adjusted Service Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'adjustedServiceDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('qualificationType')">
              <span>Qualification Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'qualificationType'"></jhi-sort-indicator>
            </th>
            <th scope="row" @click="changeOrder('qualificationSpecification')">
              <span>Qualification Specification</span>
              <jhi-sort-indicator
                :current-order="propOrder"
                :reverse="reverse"
                :field-name="'qualificationSpecification'"
              ></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.id } }">{{ employee.id }}</router-link>
            </td>
            <td>{{ employee.fullname }}</td>
            <td>{{ employee.gradeId }}</td>
            <td>{{ employee.gradeName }}</td>
            <td>{{ employee.homePhone }}</td>
            <td>{{ employee.mobileNumber }}</td>
            <td>{{ employee.assigmentId }}</td>
            <td>{{ employee.dateOfAssignment }}</td>
            <td>{{ employee.jobName }}</td>
            <td>{{ employee.address }}</td>
            <td>{{ employee.organizationId }}</td>
            <td>{{ employee.organization }}</td>
            <td>{{ employee.nationalIdentifier }}</td>
            <td>{{ employee.uid }}</td>
            <td>{{ employee.parentDepartmentId }}</td>
            <td>{{ employee.slmMilitaryFlag }}</td>
            <td>{{ employee.militaryFirstName }}</td>
            <td>{{ employee.slmName }}</td>
            <td>{{ employee.age }}</td>
            <td>{{ formatDateShort(employee.dateOfHire) || '' }}</td>
            <td>{{ formatDateShort(employee.dateOfLastHire) || '' }}</td>
            <td>{{ employee.gender }}</td>
            <td>{{ employee.jobId }}</td>
            <td>{{ employee.nationalityCode }}</td>
            <td>{{ employee.nationality }}</td>
            <td>{{ formatDateShort(employee.personStartDate) || '' }}</td>
            <td>{{ formatDateShort(employee.originalDateOfHire) || '' }}</td>
            <td>{{ employee.supervisorId }}</td>
            <td>{{ employee.supervisorFullName }}</td>
            <td>{{ employee.parentDepartmentName }}</td>
            <td>{{ employee.sectionId }}</td>
            <td>{{ employee.sectionName }}</td>
            <td>{{ formatDateShort(employee.adjustedServiceDate) || '' }}</td>
            <td>{{ employee.qualificationType }}</td>
            <td>{{ employee.qualificationSpecification }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'EmployeeView', params: { employeeId: employee.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-show="employees && employees.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./employee.component.ts"></script>
