<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()">
        <h2 id="fajwaApplicationApp.jobVacant.home.createOrEditLabel" data-cy="JobVacantCreateUpdateHeading">
          Create or edit a Job Vacant
        </h2>
        <div>
          <div class="form-group" v-if="jobVacant.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="jobVacant.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="job-vacant-department">Department</label>
            <select class="form-control" id="job-vacant-department" data-cy="department" name="department" v-model="jobVacant.department">
              <option :value="null"></option>
              <option
                :value="jobVacant.department && departmentOption.id === jobVacant.department.id ? jobVacant.department : departmentOption"
                v-for="departmentOption in departments"
                :key="departmentOption.id"
              >
                {{ departmentOption.depName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="job-vacant-jobDesc">Job Desc</label>
            <select class="form-control" id="job-vacant-jobDesc" data-cy="jobDesc" name="jobDesc" v-model="jobVacant.jobDesc">
              <option :value="null"></option>
              <option
                :value="jobVacant.jobDesc && jobDescOption.id === jobVacant.jobDesc.id ? jobVacant.jobDesc : jobDescOption"
                v-for="jobDescOption in jobDescs"
                :key="jobDescOption.id"
              >
                {{ jobDescOption.jobName }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./job-vacant-update.component.ts"></script>
